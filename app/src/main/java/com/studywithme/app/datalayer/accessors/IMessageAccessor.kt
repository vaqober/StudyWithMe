package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.message.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IMessageAccessor {
    @GET("rooms/{id}/messages")
    suspend fun allMessages(
        @Path("id") id: Int
    ): List<Message>

    @POST("rooms/{id}/messages")
    suspend fun postMessage(
        @Body message: Message,
        @Path("id") id: Int
    ): Message
}
