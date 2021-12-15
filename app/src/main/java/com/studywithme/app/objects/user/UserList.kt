package com.studywithme.app.objects.user

import com.google.gson.annotations.SerializedName
import com.studywithme.app.objects.AbstractObject

class UserList<TYPE> : AbstractObject() {
    @SerializedName("users") var users = emptyList<TYPE>()
}
