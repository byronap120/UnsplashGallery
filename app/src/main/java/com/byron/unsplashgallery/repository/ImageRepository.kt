package com.byron.unsplashgallery.repository

import com.byron.data.api.UnsplashApi
import com.byron.data.db.dao.RemoteImageDao
import com.byron.data.model.RemoteImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(
    private val unsplashApi: UnsplashApi,
    private val remoteImageDao: RemoteImageDao
) {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val imageCategory = "Guatemala"
    private val imagesPerPages = 10

    suspend fun loadImageByPage(pageIndex: Int): List<RemoteImage> {
        return withContext(ioDispatcher) {
            val response = unsplashApi.getImages(imageCategory, pageIndex, imagesPerPages)
            return@withContext response.results
        }
    }

    suspend fun loadFavoriteImages(): List<RemoteImage> {
        return withContext(ioDispatcher) {
            return@withContext remoteImageDao.favoriteImages()
        }
    }

    suspend fun saveFavoriteImage(remoteImage: RemoteImage) {
        withContext(ioDispatcher) {
            remoteImageDao.saveImage(remoteImage)
        }
    }

    suspend fun deleteFavoriteImage(remoteImage: RemoteImage) {
        withContext(ioDispatcher) {
            remoteImageDao.deleteFavoriteImage(remoteImage)
        }
    }
}