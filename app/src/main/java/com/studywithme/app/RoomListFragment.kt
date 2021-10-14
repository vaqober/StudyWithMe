package com.studywithme.app

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.studywithme.app.dummy.DummyRoomContent
import com.studywithme.app.FilterRecyclerViewAdapter.OnFilterClickListener
import com.studywithme.app.RoomRecyclerViewAdapter.OnRoomClickListener
import java.util.ArrayList


class RoomListFragment : Fragment(), OnFilterClickListener, OnRoomClickListener {

    private var columnCount = 1
    private val roomList: MutableList<RoomItem> = ArrayList(DummyRoomContent.ITEMS)
    private val filterList: MutableList<String> = ArrayList(listOf("Math", "Bath", "Kek", "Pek"))
    private val roomAdapter = RoomRecyclerViewAdapter(roomList, this)
    private val filterAdapter = FilterRecyclerViewAdapter(filterList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                RoomListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
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