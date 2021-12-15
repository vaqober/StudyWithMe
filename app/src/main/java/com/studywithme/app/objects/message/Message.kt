package com.studywithme.app.objects.message

import com.google.gson.annotations.SerializedName
import com.studywithme.app.objects.AbstractMessage

class Message(
    @SerializedName("userName") private val userName: String,
    @SerializedName("message") private val message: String,
    @SerializedName("date") private val date: Long,
    @SerializedName("userPhoto") private val userPhoto: String = "",
    @SerializedName("userId") private val userId: String = "",
    @SerializedName("id") private val id: String = ""
) : AbstractMessage() {
    override fun getId(): String = id
    override fun getUserId(): String = userId
    override fun getUserName(): String = userName
    override fun getDate(): Long = date
    override fun userPhoto(): String = userPhoto
    override fun getMessage(): String = message
}
