package com.studywithme.app.business.providers

import com.studywithme.app.objects.AbstractUser

interface IUserProvider {
    fun getMembers(roomId: Long, callback: (result: Result<List<AbstractUser>>) -> Unit)
}
