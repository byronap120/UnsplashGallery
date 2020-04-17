package com.byron.unsplashgallery.common.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.byron.unsplashgallery.R

@BindingAdapter(value = ["imageUrl"], requireAll = true)
fun bindImage(imageView: ImageView, imageUrl: String?) {
    val requestOptions = RequestOptions()
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    val context = imageView.context
    imageUrl?.let {
        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(imageView)
    }
}


@BindingAdapter(value = ["bindCircleImage"], requireAll = true)
fun bindCircleImageView(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val context = imageView.context
        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}