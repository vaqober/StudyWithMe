package com.studywithme.app.business.providers

import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.objects.user.User

interface IUserProvider {
    fun getMembers(roomId: Long, callback: (result: Result<List<AbstractUser>>) -> Unit)
    fun getAllUsers(callback: (result: Result<List<AbstractUser>>) -> Unit)
    fun postUser(user: User, callback: (result: Result<AbstractUser>) -> Unit)
}
