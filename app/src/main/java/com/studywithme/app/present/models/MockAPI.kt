package com.studywithme.app.present.models

import com.studywithme.app.objects.room.RoomDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MockAPI {
    @POST("rooms")
    fun postRoom(@Body room: RoomDto): Call<RoomDto>
}
