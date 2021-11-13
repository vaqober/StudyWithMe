package com.studywithme.app.business.providers

import com.studywithme.app.objects.AbstractRoom
import com.studywithme.app.objects.room.RoomDto

interface IRoomProvider {
    fun findAll(callback: (result: Result<List<AbstractRoom>>) -> Unit)
    fun postRoom(room: RoomDto, callback: (result: Result<AbstractRoom>) -> Unit)
}
