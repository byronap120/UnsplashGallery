package com.byron.unsplashgallery

import android.app.Application
import com.byron.data.di.dataModule
import com.byron.unsplashgallery.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class UnsplashGallery : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@UnsplashGallery)
            modules(listOf(mainModule, dataModule))
        }
    }
}