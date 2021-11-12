package com.studywithme.app.objects.room

import com.studywithme.app.objects.AbstractObject
import kotlinx.serialization.SerialName

class RoomList<TYPE>: AbstractObject() {
	@SerialName("rooms") var rooms = emptyList<TYPE>()
}