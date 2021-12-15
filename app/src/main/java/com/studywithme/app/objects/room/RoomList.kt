package com.studywithme.app.objects.room

import com.google.gson.annotations.SerializedName
import com.studywithme.app.objects.AbstractObject

class RoomList<TYPE> : AbstractObject() {
    @SerializedName("rooms") var rooms = emptyList<TYPE>()
}
