package com.studywithme.app.objects.room

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.studywithme.app.objects.AbstractRoom

@Serializable
class Room(
	@SerialName("id") private val id: Long = -1L,
	@SerialName("title") private val title: String,
	@SerialName("theme") private val theme: String = "",
	@SerialName("description") private val description: String = "",
	@SerialName("isPrivate") private val isPrivate: Boolean = false,
	@SerialName("photoUri") private val photoUri: String = ""
) : AbstractRoom() {
	override fun getId(): Long = id
	override fun getTitle(): String = title
	override fun getTheme(): String = theme
	override fun getDescription(): String = description
	override fun isPrivate(): Boolean = isPrivate
	override fun getPhotoUri(): String = photoUri

	override fun toString(): String {
		return "Room:\n title: '$title'\ntheme: '$theme'\ndescription: '$description'"
	}
}