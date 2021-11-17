package com.studywithme.app.business.providers

import com.studywithme.app.datalayer.accessors.IUserAccessor
import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.objects.user.User
import com.studywithme.app.objects.user.UserDto
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

    override fun getAllUsers(callback: (result: Result<List<AbstractUser>>) -> Unit) {
        AbstractCoroutineProvider.scope.launch {

            val result = try {
                val apiResult = (onlineAccessor.getAllUsers().users as List<AbstractUser>)
                Result.Success(apiResult)
            } catch (error: IllegalStateException) {
                Result.Fail(error)
            }

            returnResult(result, callback)
        }
    }


    override fun postUser(user: User, callback: (result: Result<AbstractUser>) -> Unit) {
        AbstractCoroutineProvider.scope.launch {

            val result = try {
                val apiResult = onlineAccessor.postUser(user) as AbstractUser
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
