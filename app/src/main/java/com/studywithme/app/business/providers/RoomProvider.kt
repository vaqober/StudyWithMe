package com.studywithme.app.business.providers

import com.studywithme.app.business.providers.AbstractCoroutineProvider.Companion.scope
import com.studywithme.app.datalayer.accessors.IRoomAccessor
import com.studywithme.app.objects.AbstractRoom
import com.studywithme.app.objects.room.RoomDto
import java.lang.IllegalStateException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomProvider(private val onlineAccessor: IRoomAccessor) :
    AbstractCoroutineProvider, IRoomProvider {
    override fun findAll(callback: (result: Result<List<AbstractRoom>>) -> Unit) {
        scope.launch {
            val result = try {
                val apiResult = onlineAccessor.findAll().rooms as List<AbstractRoom>
                Result.Success(apiResult)
            } catch (error: IllegalStateException) {
                Result.Fail(error)
            }
            returnResult(result, callback)
        }
    }

    override fun postRoom(room: RoomDto, callback: (result: Result<AbstractRoom>) -> Unit) {
        scope.launch {

            val result = try {
                val apiResult = onlineAccessor.postRoom(room) as AbstractRoom
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
