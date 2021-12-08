package com.studywithme.app.business.providers

import com.studywithme.app.objects.AbstractMessage
import com.studywithme.app.objects.message.Message

interface IMessageProvider {
    fun allMessages(
        roomId: Int,
        query: String,
        callback: (result: Result<List<AbstractMessage>>) -> Unit
    )
    fun postMessage(
        roomId: Int,
        message: Message,
        callback: (result: Result<AbstractMessage>) -> Unit
    )
}
