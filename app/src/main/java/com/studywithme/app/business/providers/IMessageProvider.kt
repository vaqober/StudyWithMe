package com.studywithme.app.business.providers

import com.studywithme.app.objects.AbstractMessage
import com.studywithme.app.objects.message.Message

interface IMessageProvider {
    fun allMessages(
        roomId: String,
        query: String,
        callback: (result: Result<List<AbstractMessage>>) -> Unit
    )
    fun postMessage(
        roomId: String,
        message: Message,
        callback: (result: Result<AbstractMessage>) -> Unit
    )
}
