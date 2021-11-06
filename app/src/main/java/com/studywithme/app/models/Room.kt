package com.studywithme.app.models

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("photoUri")
    val photo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("theme")
    val theme: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("isPrivate")
    val isPrivate: Boolean
)
