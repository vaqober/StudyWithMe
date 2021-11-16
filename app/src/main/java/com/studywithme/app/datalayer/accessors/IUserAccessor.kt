package com.studywithme.app.datalayer.accessors

import com.studywithme.app.objects.user.User
import com.studywithme.app.objects.user.UserList
import retrofit2.http.GET

interface IUserAccessor {
    @GET("users")
    suspend fun getMembers(): UserList<User>
}