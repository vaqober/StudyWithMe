package com.studywithme.app.datalayer.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl = request.url.newBuilder()
            // .addQueryParameter(APIKEY, apiKey)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        const val APIKEY = "apikey"
    }
}
