package com.studywithme.app.present.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studywithme.app.R

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
        holder.bind(values[position], listener, position)
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val filterView: TextView = view.findViewById(R.id.filter)

        fun bind(filter: String, listener: OnFilterClickListener, position: Int) {
            filterView.text = filter
            itemView.setOnClickListener {
                listener.onFilterClick(position)
            }
        }

        override fun toString(): String {
            return super.toString() + " filter: '${filterView.text}'"
        }
    }

    interface OnFilterClickListener {
        fun onFilterClick(position: Int)
    }
}
