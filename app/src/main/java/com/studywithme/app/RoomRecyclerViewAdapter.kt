package com.studywithme.app

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RoomRecyclerViewAdapter(
        private val values: List<RoomItem>,
        private val listener: OnRoomClickListener
)
    : RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_room_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.themeView.text = item.theme
        holder.descriptionView.text = item.description
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val titleView: TextView = view.findViewById(R.id.title)
        val descriptionView: TextView = view.findViewById(R.id.description)
        val themeView: TextView = view.findViewById(R.id.theme)

        init {
            view.setOnClickListener(this)
        }

        override fun toString(): String {
            return super.toString() + " title: '" + titleView.text + "'\n" + "theme: '" + themeView.text + "'\n" + "description: '" + descriptionView.text + "'\n"
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onRoomClick(position)
            }
        }
    }

    interface OnRoomClickListener {
        fun onRoomClick(position: Int)
    }
}