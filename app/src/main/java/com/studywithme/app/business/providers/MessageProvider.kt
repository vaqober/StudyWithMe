package com.studywithme.app.business.providers

import com.studywithme.app.business.providers.AbstractCoroutineProvider.Companion.scope
import com.studywithme.app.datalayer.accessors.IMessageAccessor
import com.studywithme.app.objects.AbstractMessage
import com.studywithme.app.objects.message.Message
import java.lang.IllegalStateException
import kotlinx.coroutines.launch

class MessageProvider(private val onlineAccessor: IMessageAccessor) :
    AbstractCoroutineProvider, IMessageProvider {
    override fun allMessages(
        roomId: Int,
        query: String,
        callback: (result: Result<List<AbstractMessage>>) -> Unit
    ) {
        scope.launch {
            val result = try {
                val apiResult =
                    (onlineAccessor.allMessages(roomId) as List<AbstractMessage>).filter {
                        it.getMessage().contains(query)
                    }
                Result.Success(apiResult)
            } catch (error: IllegalStateException) {
                Result.Fail(error)
            }
            returnResult(result, callback)
        }
    }

    override fun postMessage(
        roomId: Int,
        message: Message,
        callback: (result: Result<AbstractMessage>) -> Unit
    ) {
        scope.launch {

            val result = try {
                val apiResult = onlineAccessor.postMessage(message, roomId) as AbstractMessage
                Result.Success(apiResult)
            } catch (error: IllegalStateException) {
                Result.Fail(error)
            }

            returnResult(result, callback)
        }
    }
}
