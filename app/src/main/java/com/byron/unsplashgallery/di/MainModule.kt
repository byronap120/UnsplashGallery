package com.byron.unsplashgallery.di

import com.byron.unsplashgallery.favoriteFeed.FavoriteFeedViewModel
import com.byron.unsplashgallery.imageFeed.ImageFeedViewModel
import com.byron.unsplashgallery.repository.ImageRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single { ImageRepository(get(), get()) }

    viewModel { ImageFeedViewModel(get()) }

    viewModel { FavoriteFeedViewModel(get()) }

}