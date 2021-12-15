package com.studywithme.app.objects.message

import com.google.gson.annotations.SerializedName
import com.studywithme.app.objects.AbstractObject

class MessageList<TYPE> : AbstractObject() {
    @SerializedName("messages")
    var messages = emptyList<TYPE>()
}
