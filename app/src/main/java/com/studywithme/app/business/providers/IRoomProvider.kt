package com.studywithme.app.business.providers

import com.studywithme.app.objects.AbstractRoom
import com.studywithme.app.objects.room.Room

interface IRoomProvider {
    fun findRooms(query: String, callback: (result: Result<List<AbstractRoom>>) -> Unit)
    fun postRoom(room: Room, callback: (result: Result<AbstractRoom>) -> Unit)
}
