package com.studywithme.app.business.providers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AbstractCoroutineProvider {
    companion object {
        val scope = CoroutineScope(Dispatchers.IO)
    }

    suspend fun <TYPE> returnResult(
        result: Result<TYPE>,
        callback: (Result<TYPE>) -> Unit
    ) {
        withContext(Dispatchers.Main) {
            callback(result)
        }
    }
}
