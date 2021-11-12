package com.studywithme.app.modules

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.studywithme.app.business.providers.IRoomProvider
import com.studywithme.app.business.providers.RoomProvider
import com.studywithme.app.datalayer.accessors.IRoomAccessor
import company.vk.education.lection07.datalayer.interceptors.RoomInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalSerializationApi
val roomsModule = module() {
    factory<IRoomProvider> { RoomProvider(get()) }

    factory<IRoomAccessor> {
        val openWeatherUrl = "https://6161de9737492500176314c6.mockapi.io/api/develop/v1/"
        val openWeatherKey = "<SET HERE API_KEY>"

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(RoomInterceptor(openWeatherKey))
            .addNetworkInterceptor(loggingInterceptor)
            .build()


        val gson = GsonBuilder()
            .create()

        val gsonConverter = GsonConverterFactory.create(gson)


        Retrofit.Builder()
            .baseUrl(openWeatherUrl)
            .addConverterFactory(gsonConverter)
            .client(client)
            .build()
            .create(IRoomAccessor::class.java)
    }
}