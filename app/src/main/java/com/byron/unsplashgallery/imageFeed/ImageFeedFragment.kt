package com.byron.unsplashgallery.imageFeed

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byron.data.model.RemoteImage
import com.byron.unsplashgallery.R
import com.byron.unsplashgallery.common.adapter.ImageFeedAdapter
import com.byron.unsplashgallery.databinding.FragmentImageFeedBinding
import kotlinx.android.synthetic.main.fragment_image_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ImageFeedFragment : Fragment(), ImageFeedAdapter.OnItemClickListener {

    private val viewModel by viewModel<ImageFeedViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ImageFeedAdapter
    private lateinit var viewDataBinding: FragmentImageFeedBinding
    private lateinit var viewLayoutManager: LinearLayoutManager
    private var loading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_image_feed, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolBar()
        initializeRecyclerView()
        initializeObservers()
        viewModel.getImagePage()
    }

    override fun onItemClick(remoteImage: RemoteImage) {
        val direction =
            ImageFeedFragmentDirections.imageFeedFragmentToDetailImageFragment(remoteImage)
        findNavController().navigate(direction)
    }

    override fun onActionItemClick(remoteImage: RemoteImage) {
        viewModel.saveFavoriteImage(remoteImage)
        val toast = Toast.makeText(context, R.string.favorite_text, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initializeToolBar() {
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.title)
        setHasOptionsMenu(true)
    }

    private fun initializeRecyclerView() {
        viewAdapter =
            ImageFeedAdapter(
                this,
                false
            )
        viewLayoutManager = LinearLayoutManager(context)
        recyclerView = viewDataBinding.images.apply {
            layoutManager = viewLayoutManager
            adapter = viewAdapter
        }
        addScrollListenerPagination(recyclerView)
    }

    private fun addScrollListenerPagination(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = viewLayoutManager.childCount
                val totalItemCount = viewLayoutManager.itemCount
                val firstVisible = viewLayoutManager.findFirstVisibleItemPosition()
                if (!loading && (visibleItemCount + firstVisible) >= totalItemCount) {
                    loading = true
                    viewModel.requireNewImagePage()
                }
            }
        })
    }

    private fun initializeObservers() {
        viewModel.images.observe(this.viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                progressBar.visibility = View.INVISIBLE
            }
            viewAdapter.updateImages(it)
            viewAdapter.notifyDataSetChanged()
            loading = false
        })
    }

}
