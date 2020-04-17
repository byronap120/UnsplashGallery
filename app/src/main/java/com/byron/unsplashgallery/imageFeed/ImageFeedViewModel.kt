package com.byron.unsplashgallery.imageFeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byron.data.model.RemoteImage
import com.byron.unsplashgallery.repository.ImageRepository
import kotlinx.coroutines.launch

class ImageFeedViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    private val _images = MutableLiveData<List<RemoteImage>>()
    val images: LiveData<List<RemoteImage>> = _images

    private val _pageIndex = MutableLiveData<Int>()

    init {
        _pageIndex.value = 1
        _images.value = listOf()
    }

    fun getImagePage() {
        viewModelScope.launch {
            val newImagesList = mutableListOf<RemoteImage>()
            newImagesList.addAll(_images.value!!)

            val remoteImages = imageRepository.loadImageByPage(_pageIndex.value!!)
            newImagesList.addAll(remoteImages)

            _images.value = newImagesList
        }
    }

    fun requireNewImagePage() {
        _pageIndex.value = _pageIndex.value?.plus(1)
        getImagePage()
    }

    fun saveFavoriteImage(remoteImage: RemoteImage) {
        viewModelScope.launch {
            imageRepository.saveFavoriteImage(remoteImage)
        }
    }

}
