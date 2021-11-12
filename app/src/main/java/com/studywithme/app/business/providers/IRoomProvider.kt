package com.studywithme.app.business.providers

import com.studywithme.app.objects.AbstractRoom

interface IRoomProvider {
	fun findAll(callback: (result: Result<List<AbstractRoom>>) -> Unit)
}