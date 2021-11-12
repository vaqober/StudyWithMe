package com.studywithme.app.business.providers

import com.studywithme.app.datalayer.accessors.IRoomAccessor
import com.studywithme.app.objects.AbstractRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomProvider(private val onlineAccessor: IRoomAccessor)
	: AbstractCoroutineProvider(), IRoomProvider {
	override fun findAll(callback: (result: Result<List<AbstractRoom>>) -> Unit) {
		scope.launch {

			val result = try {
				val apiResult = onlineAccessor.findAll().rooms as List<AbstractRoom>
				Result.Success(apiResult)
			} catch (error: Throwable) {
				Result.Fail(error)
			}

			withContext(Dispatchers.Main) {
				callback(result)
			}
		}
	}
}