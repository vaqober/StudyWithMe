package com.studywithme.app.objects.room

import com.studywithme.app.objects.AbstractRoom
import kotlinx.serialization.SerialName

class Room(
    @SerialName("id") private val id: String = "",
    @SerialName("title") private val title: String,
    @SerialName("theme") private val theme: String = "",
    @SerialName("description") private val description: String = "",
    @SerialName("isPrivate") private val isPrivate: Boolean = false,
    @SerialName("photoUri") private val photoUri: String = ""
) : AbstractRoom() {
    override fun getId(): String = id
    override fun getTitle(): String = title
    override fun getTheme(): String = theme
    override fun getDescription(): String = description
    override fun isPrivate(): Boolean = isPrivate
    override fun getPhotoUri(): String = photoUri

    override fun toString(): String {
        return "Room:\n title: '$title'\ntheme: '$theme'\ndescription: '$description'"
    }
}
