package com.studywithme.app.present.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.studywithme.app.R
import com.studywithme.app.business.providers.IGlideProvider
import com.studywithme.app.databinding.FragmentRoomListItemBinding
import com.studywithme.app.objects.room.Room

class RoomRecyclerViewAdapter(
    val values: MutableList<Room>,
    private val listener: OnRoomClickListener,
    private val providerGlide: IGlideProvider
) :
    RecyclerView.Adapter<RoomRecyclerViewAdapter.RoomItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_room_list_item, parent, false)
        return RoomItemViewHolder(view, providerGlide)
    }

    override fun onBindViewHolder(holder: RoomItemViewHolder, position: Int) {
        holder.bind(values[position], listener)
    }

    override fun getItemCount(): Int = values.size

    class RoomItemViewHolder(view: View, private val providerGlide: IGlideProvider) :
        RecyclerView.ViewHolder(
            view
        ) {

        val binding = FragmentRoomListItemBinding.bind(view)

        fun bind(room: Room, listener: OnRoomClickListener) = with(binding) {
            title.text = room.getTitle()
            theme.text = room.getTheme()
            description.text = room.getDescription()
            providerGlide.loadImage(room.getPhotoUri(), itemImage, isCircle = true)
            itemView.setOnClickListener {
                listener.onRoomClick(room.getId().toLong())
            }
        }

        override fun toString(): String {
            return super.toString() +
                " title: '" + binding.title.text +
                "'\n" + "theme: '" + binding.theme.text +
                "'\n" + "description: '" + binding.description.text + "'\n"
        }
    }

    interface OnRoomClickListener {
        fun onRoomClick(position: Long)
    }

    fun update(newList: List<Room>) {
        val messageDiffUtilCallback =
            RoomDiffUtilCallback(values, newList)
        DiffUtil.calculateDiff(messageDiffUtilCallback).dispatchUpdatesTo(this)
    }

    class RoomDiffUtilCallback(
        private val mOldList: List<Room>,
        private val mNewList: List<Room>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = mOldList.size

        override fun getNewListSize(): Int = mNewList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            mOldList[oldItemPosition] == mNewList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            mOldList[oldItemPosition].getId() == mNewList[newItemPosition].getId() &&
                mOldList[oldItemPosition].getTheme() == mNewList[newItemPosition].getTheme() &&
                mOldList[oldItemPosition].getDescription() ==
                mNewList[newItemPosition].getDescription() &&
                mOldList[oldItemPosition].getTitle() == mNewList[newItemPosition].getTitle()
    }
}
