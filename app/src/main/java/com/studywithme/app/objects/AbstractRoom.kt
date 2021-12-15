package com.studywithme.app.objects

import com.studywithme.app.objects.message.Message

abstract class AbstractRoom : AbstractObject() {
    abstract fun getId(): String
    abstract fun getTitle(): String
    abstract fun getTheme(): String
    abstract fun getDescription(): String
    abstract fun getPhotoUri(): String
    abstract fun getMessagesList(): MutableList<Message>
}
