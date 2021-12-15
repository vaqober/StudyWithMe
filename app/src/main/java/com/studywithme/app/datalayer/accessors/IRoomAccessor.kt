package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.room.Room
import com.studywithme.app.objects.room.RoomList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IRoomAccessor {
    @GET("rooms.json")
    suspend fun findAll(): RoomList<Room>

    @POST("rooms/rooms.json")
    suspend fun postRoom(@Body room: Room): Room
}
