package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.user.User
import com.studywithme.app.objects.user.UserList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IUserAccessor {
    @GET("users")
    suspend fun getMembers(): UserList<User>

    @GET("users")
    suspend fun getAllUsers(): UserList<User>

    @POST("users")
    suspend fun postUser(@Body user: User): User
}
