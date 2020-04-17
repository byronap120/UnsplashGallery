package com.byron.unsplashgallery.favoriteFeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byron.data.model.RemoteImage
import com.byron.unsplashgallery.repository.ImageRepository
import kotlinx.coroutines.launch

class FavoriteFeedViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    private val _images = MutableLiveData<List<RemoteImage>>()
    val images: LiveData<List<RemoteImage>> = _images

    fun getFavoriteImages() {
        viewModelScope.launch {
            val result = imageRepository.loadFavoriteImages()
            _images.value = result
        }
    }

    fun deleteFavoriteImage(remoteImage: RemoteImage) {
        viewModelScope.launch {
            imageRepository.deleteFavoriteImage(remoteImage)
        }
    }

}
