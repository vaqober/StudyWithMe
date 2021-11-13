package com.studywithme.app.objects.room

import com.google.gson.annotations.SerializedName
import com.studywithme.app.objects.AbstractRoom

class Room(
    @SerializedName("id") private val id: String = "",
    @SerializedName("title") private val title: String,
    @SerializedName("theme") private val theme: String = "",
    @SerializedName("description") private val description: String = "",
    @SerializedName("isPrivate") private val isPrivate: Boolean = false,
    @SerializedName("photoUri") private val photoUri: String = ""
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
