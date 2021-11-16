package com.studywithme.app.objects.user

import com.google.gson.annotations.SerializedName
import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.objects.room.Room

class User(
    @SerializedName("id") private val id: String = "",
    @SerializedName("name") private val name: String,
    @SerializedName("photoUri") private val photoUri: String = "",
    @SerializedName("description") private val description: String = "",
    @SerializedName("roomsList") private val roomsList: MutableList<Room>,
    @SerializedName("isOnline") private val isOnline: Boolean = false
) : AbstractUser() {

    override fun getId(): String = id
    override fun getName(): String = name
    override fun getPhotoUri(): String = photoUri
    override fun getDescription(): String = description
    override fun getRoomsList(): MutableList<Room> = roomsList
    override fun isOnline(): Boolean = isOnline

    override fun toString(): String {
        return "name: '$name'"
    }
}
