package com.studywithme.app.objects.user

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    private val id: String,
    @SerializedName("name")
    private val name: String,
    @SerializedName("photoUri")
    private val photoUri: String,
    @SerializedName("description")
    private val description: String,
//    @SerializedName("friendsList")
//    private val friendsList: MutableList<User>,
    @SerializedName("roomsList")
    private val roomsList: MutableList<Long>,
    @SerializedName("isOnline")
    private val isOnline: Boolean,
)
