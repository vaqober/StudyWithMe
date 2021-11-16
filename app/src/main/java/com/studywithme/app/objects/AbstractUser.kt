package com.studywithme.app.objects

import com.studywithme.app.objects.room.Room
import com.studywithme.app.objects.user.User

abstract class AbstractUser : AbstractObject() {
    abstract fun getId(): String
    abstract fun getName(): String
    abstract fun getPhotoUri(): String
    abstract fun getDescription(): String
    abstract fun getRoomsList(): MutableList<Room>
    abstract fun isOnline(): Boolean
}
