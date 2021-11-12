package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.room.Room
import com.studywithme.app.objects.room.RoomList
import retrofit2.http.GET
import retrofit2.http.Query

interface IRoomAccessor {
	@GET("rooms")
	suspend fun findAll(): RoomList<Room>
}