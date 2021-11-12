package com.studywithme.app.dummy

import com.studywithme.app.objects.room.Room
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyRoomContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Room> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Room> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i.toString()))
        }
    }

    private fun addItem(item: Room) {
        ITEMS.add(item)
        ITEM_MAP[item.getId()] = item
    }

    private fun createDummyItem(position: String): Room {
        return Room(
            position,
            "Math room " + position,
            "Math",
            "Math study room. Lorem ipsum tadalala ulala"
        )
    }
}
