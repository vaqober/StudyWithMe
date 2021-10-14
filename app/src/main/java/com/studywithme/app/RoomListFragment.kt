package com.studywithme.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studywithme.app.FilterRecyclerViewAdapter.OnFilterClickListener
import com.studywithme.app.RoomRecyclerViewAdapter.OnRoomClickListener
import com.studywithme.app.dummy.DummyRoomContent
import java.util.ArrayList

class RoomListFragment : Fragment(), OnFilterClickListener, OnRoomClickListener {

    private var columnCount = 1
    private val roomList: MutableList<RoomItem> = ArrayList(DummyRoomContent.ITEMS)
    private val filterList: MutableList<String> = ArrayList(listOf("Math", "Bath", "Kek", "Pek"))
    private val roomAdapter = RoomRecyclerViewAdapter(roomList, this)
    private val filterAdapter = FilterRecyclerViewAdapter(filterList, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room_list_list, container, false)
        val filters = view.findViewById<RecyclerView>(R.id.filter_list)
        filters.adapter = filterAdapter
        val viewRecycler = view.findViewById<RecyclerView>(R.id.room_list)
        // Set the adapter
        with(viewRecycler) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = roomAdapter
        }
        activity?.setTitle(R.string.fragment_rooms_title)
        return view
    }

    override fun onFilterClick(position: Int) {
        Toast.makeText(context, "Filter $position clicked", Toast.LENGTH_SHORT).show()
        filterList.removeAt(position)
        filterAdapter.notifyDataSetChanged()
    }

    override fun onRoomClick(position: Int) {
        Toast.makeText(context, "Room $position clicked", Toast.LENGTH_SHORT).show()
        roomList.removeAt(position)
        roomAdapter.notifyDataSetChanged()
    }
}
