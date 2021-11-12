package com.studywithme.app.business.providers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class AbstractCoroutineProvider {
	protected val scope = CoroutineScope(Dispatchers.IO)

	protected suspend fun <TYPE> returnResult(
		result: Result<TYPE>,
		callback: (Result<TYPE>) -> Unit
	) {
		withContext(Dispatchers.Main) {
			callback(result)
		}
	}
}