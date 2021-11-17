package com.studywithme.app.modules

import com.google.gson.GsonBuilder
import com.studywithme.app.business.providers.IUserProvider
import com.studywithme.app.business.providers.UserProvider
import com.studywithme.app.datalayer.accessors.IUserAccessor
import com.studywithme.app.datalayer.interceptors.AppInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val usersModule = module() {
    factory<IUserProvider> { UserProvider(get()) }

    factory<IUserAccessor> {
        val baseUrl = "https://6161de9737492500176314c6.mockapi.io/api/develop/v1/"
        val baseKey = "<SET HERE API_KEY>"

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AppInterceptor(baseKey))
            .addNetworkInterceptor(loggingInterceptor)
            .build()

        val gson = GsonBuilder()
            .create()

        val gsonConverter = GsonConverterFactory.create(gson)

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverter)
            .client(client)
            .build()
            .create(IUserAccessor::class.java)
    }
}
