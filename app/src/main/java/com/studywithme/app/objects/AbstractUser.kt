package com.studywithme.app.objects

abstract class AbstractUser : AbstractObject() {
    abstract fun getId(): String
    abstract fun getName(): String
    abstract fun getPhotoUri(): String
    abstract fun getDescription(): String
    abstract fun getRoomsList(): MutableList<String>
    abstract fun isOnline(): Boolean
}
