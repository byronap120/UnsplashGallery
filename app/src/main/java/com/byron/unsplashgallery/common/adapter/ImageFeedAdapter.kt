package com.byron.unsplashgallery.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.byron.data.model.RemoteImage
import com.byron.unsplashgallery.R
import com.byron.unsplashgallery.databinding.ImageFeedItemBinding
import java.util.*
import kotlin.collections.ArrayList


class ImageFeedAdapter(
    private val listener: OnItemClickListener,
    private val favoriteList: Boolean
) :
    RecyclerView.Adapter<ImageFeedAdapter.ImageFeedHolder>(), Filterable {

    private var imageList = listOf<RemoteImage>()
    private var filteredImageList = mutableListOf<RemoteImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFeedHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ImageFeedItemBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.image_feed_item,
            parent,
            false
        )
        return ImageFeedHolder(
            binding,
            favoriteList
        )
    }

    override fun getItemCount(): Int {
        return filteredImageList.size
    }

    override fun onBindViewHolder(holder: ImageFeedHolder, position: Int) {
        holder.bind(filteredImageList[position], listener)
    }

    fun updateImages(remoteImages: List<RemoteImage>) {
        this.imageList = remoteImages
        this.filteredImageList = remoteImages.toMutableList()
    }

    class ImageFeedHolder(
        private val binding: ImageFeedItemBinding,
        private val favoriteList: Boolean
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(remoteImage: RemoteImage, listener: OnItemClickListener) {
            binding.image = remoteImage

            binding.root.setOnClickListener {
                listener.onItemClick(remoteImage)
            }

            binding.removeFavorite.setOnClickListener {
                listener.onActionItemClick(remoteImage)
            }

            binding.addToFavorite.setOnClickListener {
                listener.onActionItemClick(remoteImage)
            }

            if (favoriteList) {
                binding.removeFavorite.visibility = View.VISIBLE
                binding.addToFavorite.visibility = View.INVISIBLE
            } else {
                binding.removeFavorite.visibility = View.INVISIBLE
                binding.addToFavorite.visibility = View.VISIBLE
            }

        }
    }

    override fun getFilter(): Filter {
        return imageFilter
    }

    private val imageFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<RemoteImage> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(imageList)
            } else {
                val filterPattern =
                    constraint.toString().trim().toLowerCase(Locale.ROOT)
                for (item in imageList) {
                    if (item.user.name.toLowerCase(Locale.ROOT).contains(filterPattern) ||
                        item.user.username.toLowerCase(Locale.ROOT).contains(filterPattern)
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            with(filteredImageList) {
                clear()
                addAll(results.values as List<RemoteImage>)
            }
            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(remoteImage: RemoteImage)

        fun onActionItemClick(remoteImage: RemoteImage)
    }

}