package com.studywithme.app.business.providers

import com.studywithme.app.business.providers.AbstractCoroutineProvider.Companion.scope
import com.studywithme.app.datalayer.accessors.IRoomAccessor
import com.studywithme.app.objects.AbstractRoom
import com.studywithme.app.objects.room.Room
import java.lang.IllegalStateException
import kotlinx.coroutines.launch

class RoomProvider(private val onlineAccessor: IRoomAccessor) :
    AbstractCoroutineProvider, IRoomProvider {
    override fun findRooms(query: String, callback: (result: Result<List<AbstractRoom>>) -> Unit) {
        scope.launch {
            val result = try {
                val apiResult = (onlineAccessor.findAll().rooms as List<AbstractRoom>).filter {
                    it.getTitle().contains(query)
                }
                Result.Success(apiResult)
            } catch (error: IllegalStateException) {
                Result.Fail(error)
            }
            returnResult(result, callback)
        }
    }

    override fun postRoom(room: Room, callback: (result: Result<AbstractRoom>) -> Unit) {
        scope.launch {

            val result = try {
                val apiResult = onlineAccessor.postRoom(room) as AbstractRoom
                Result.Success(apiResult)
            } catch (error: IllegalStateException) {
                Result.Fail(error)
            }

            returnResult(result, callback)
        }
    }
}
