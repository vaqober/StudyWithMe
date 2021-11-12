package com.studywithme.app

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.studywithme.app.present.models.MockAPI
import com.studywithme.app.objects.room.RoomDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object MockDataStore {
    val contentType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://6161de9737492500176314c6.mockapi.io/api/develop/v1/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    private val mockApi = retrofit.create(MockAPI::class.java)

    suspend fun postRoom(room: RoomDto): RoomDto = withContext(Dispatchers.IO) {
        return@withContext mockApi.postRoom(room).execute().body() ?: error("error")
    }
}
