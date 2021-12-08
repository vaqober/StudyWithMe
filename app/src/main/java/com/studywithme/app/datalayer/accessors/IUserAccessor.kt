package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.user.User
import com.studywithme.app.objects.user.UserList
import retrofit2.http.*

interface IUserAccessor {
    @GET("users")
    suspend fun getMembers(): UserList<User>

    @GET("users")
    suspend fun getAllUsers(): UserList<User>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User

    @PUT("users/{id}")
    suspend fun putUser(@Path("id") userId: String, @Body user: User): User

    @POST("users")
    suspend fun postUser(@Body user: User): User
}
