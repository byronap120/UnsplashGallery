package com.byron.data.api

import com.byron.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("search/photos/")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ImageResponse
}