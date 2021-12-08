package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.user.User
import com.studywithme.app.objects.user.UserList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface IUserAccessor {
    @GET("users")
    suspend fun getMembers(): UserList<User>

    @GET("users")
    suspend fun getAllUsers(): UserList<User>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): User

    @PUT("users/{id}")
    suspend fun postUser(@Path("id") userId: String, @Body user: User): User
}
