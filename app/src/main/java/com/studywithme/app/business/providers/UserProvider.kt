package com.studywithme.app.business.providers

import com.studywithme.app.datalayer.accessors.IUserAccessor
import com.studywithme.app.objects.AbstractUser
import java.lang.IllegalStateException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProvider(private val onlineAccessor: IUserAccessor) :
    AbstractCoroutineProvider, IUserProvider {

    override fun getMembers(roomId: Long, callback: (result: Result<List<AbstractUser>>) -> Unit) {
        AbstractCoroutineProvider.scope.launch {

            val result = try {
                val apiResult = (onlineAccessor.getMembers().users as List<AbstractUser>).filter {
                    it.getRoomsList().contains(roomId)
                }
                Result.Success(apiResult)
            } catch (error: IllegalStateException) {
                Result.Fail(error)
            }

            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }
}
