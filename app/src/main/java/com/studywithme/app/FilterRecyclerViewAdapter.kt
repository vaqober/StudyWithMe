package com.studywithme.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilterRecyclerViewAdapter(
        private val values: List<String>,
        private val listener: OnFilterClickListener
) :
        RecyclerView.Adapter<FilterRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.filterView.text = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val filterView: TextView = view.findViewById(R.id.filter)
        val imageView: ImageButton = view.findViewById(R.id.filter_delete)

        init {
            filterView.setOnClickListener(this)
            imageView.setOnClickListener(this)
        }

        override fun toString(): String {
            return super.toString() + " filter: '${filterView.text}'"
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onFilterClick(position)
            }
        }
    }

    interface OnFilterClickListener {
        fun onFilterClick(position: Int)
    }
}
