package com.byron.unsplashgallery.favoriteFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.byron.unsplashgallery.databinding.FragmentFavoriteFeedBinding
import kotlinx.android.synthetic.main.fragment_favorite_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFeedFragment : Fragment(), ImageFeedAdapter.OnItemClickListener {

    private val viewModel by viewModel<FavoriteFeedViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ImageFeedAdapter
    private lateinit var viewDataBinding: FragmentFavoriteFeedBinding
    private lateinit var viewLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_feed, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolBar()
        initializeRecyclerView()
        initializeObservers()
        viewModel.getFavoriteImages()
    }

    override fun onItemClick(remoteImage: RemoteImage) {
        val direction =
            FavoriteFeedFragmentDirections.favoriteFeedFragmentToDetailImageFragment(remoteImage)
        findNavController().navigate(direction)
    }

    override fun onActionItemClick(remoteImage: RemoteImage) {
        viewModel.deleteFavoriteImage(remoteImage)
        viewModel.getFavoriteImages()
        val toast = Toast.makeText(context, R.string.favorite_removed_text, Toast.LENGTH_SHORT)
        toast.show()

    }

    private fun initializeToolBar() {
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.titleFavorite)
        setHasOptionsMenu(true)
    }

    private fun initializeRecyclerView() {
        viewAdapter =
            ImageFeedAdapter(this, true)
        viewLayoutManager = LinearLayoutManager(context)
        recyclerView = viewDataBinding.images.apply {
            layoutManager = viewLayoutManager
            adapter = viewAdapter
        }
    }

    private fun initializeObservers() {
        viewModel.images.observe(this.viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                errorMessage.visibility = View.INVISIBLE
            } else {
                errorMessage.visibility = View.VISIBLE
            }
            viewAdapter.updateImages(it)
            viewAdapter.notifyDataSetChanged()
        })
    }

}
