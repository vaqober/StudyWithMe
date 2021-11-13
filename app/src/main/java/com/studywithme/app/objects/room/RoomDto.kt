package com.studywithme.app.objects.room

import com.google.gson.annotations.SerializedName

data class RoomDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("theme")
    val theme: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("photoUri")
    val photoUri: String?,
    @SerializedName("isPrivate")
    val isPrivate: Boolean
)
