package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.user.User
import com.studywithme.app.objects.user.UserList
import retrofit2.http.*

interface IUserAccessor {
    @GET("users.json")
    suspend fun getMembers(): UserList<User>

    @GET("users.json")
    suspend fun getAllUsers(): UserList<User>

    @GET("users/users/{id}.json")
    suspend fun getUserById(@Path("id") userId: String): User

    @PUT("users/users/{id}.json")
    suspend fun putUser(@Path("id") userId: String, @Body user: User): User

    @POST("users/users.json")
    suspend fun postUser(@Body user: User): User
}
