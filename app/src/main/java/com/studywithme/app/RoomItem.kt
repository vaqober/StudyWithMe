package com.studywithme.app

data class RoomItem(val id: String, val title: String, val theme: String, val description: String) {
    override fun toString(): String = " title: '$title'\ntheme: '$theme'\ndescription: '$description'"
}
