package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.message.Message
import retrofit2.http.*

interface IMessageAccessor {

    @GET("rooms/rooms/{id}/messages.json")
    suspend fun allMessages(
        @Path("id") id: String
    ): List<Message>

    @POST("messages/{id}.json")
    suspend fun postMessage(
        @Body message: Message,
        @Path("id") id: String
    ): Message
}
