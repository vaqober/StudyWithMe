package com.studywithme.app.objects

abstract class AbstractMessage : AbstractObject() {
    abstract fun getUserId(): Int
    abstract fun getId(): String
    abstract fun getUserName(): String
    abstract fun getDate(): Long
    abstract fun userPhoto(): String
    abstract fun getMessage(): String
}
