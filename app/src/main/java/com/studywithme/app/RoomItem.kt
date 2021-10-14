package com.studywithme.app

data class RoomItem(val id: String, val title: String, val theme: String, val description: String) {
    override fun toString(): String {
        return " title: '$title'\ntheme: '$theme'\ndescription: '$description'"
    }
}
