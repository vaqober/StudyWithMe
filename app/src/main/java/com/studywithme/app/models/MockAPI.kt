package com.studywithme.app.models

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MockAPI {
    @POST("rooms")
    fun postRoom(@Body room: Room): Call<Room>
}
