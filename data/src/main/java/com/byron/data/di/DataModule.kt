package com.byron.data.di

import androidx.room.Room
import com.byron.data.api.AuthInterceptor
import com.byron.data.api.UnsplashApi
import com.byron.data.db.UnsplashDataBase
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Room.databaseBuilder(get(), UnsplashDataBase::class.java, "unsplash_database")
            .fallbackToDestructiveMigration().build()
    }

    single { get<UnsplashDataBase>().remoteImageDao() }

    factory { AuthInterceptor() }

    factory { provideOkHttpClient(get()) }

    factory { provideSimApi(get()) }

    single { provideRetrofit(get()) }
}


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://api.unsplash.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideSimApi(retrofit: Retrofit): UnsplashApi = retrofit.create(UnsplashApi::class.java)
