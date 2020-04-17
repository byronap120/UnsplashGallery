package com.byron.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("client_id", "SQWD1Je57O9AtWUJYeS8n-mH8H7pYUsTGsFssJPHyZA")
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}