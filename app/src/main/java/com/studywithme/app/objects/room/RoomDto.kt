package com.studywithme.app.objects.room

import kotlinx.serialization.SerialName

data class RoomDto(
    @SerialName("id")
    val id: String?,
    @SerialName("title")
    val title: String,
    @SerialName("theme")
    val theme: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("photoUri")
    val photoUrl: String?,
    @SerialName("isPrivate")
    val isPrivate: Boolean
)
