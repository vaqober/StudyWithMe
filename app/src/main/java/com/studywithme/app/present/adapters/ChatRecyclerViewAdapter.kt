package com.studywithme.app.present.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.studywithme.app.R
import com.studywithme.app.business.providers.IGlideProvider
import com.studywithme.app.objects.message.Message
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class ChatRecyclerViewAdapter(
    val values: MutableList<Message>,
    private val providerGlide: IGlideProvider
) :
    RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatItemViewHolder>() {
    companion object {
        private const val dateMult = 1000
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_chat_user_message_item, parent, false)
        return ChatItemViewHolder(view, providerGlide)
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int {
        return values.size
    }

    fun setMessage(message: Message) {
        values.add(message)
        notifyDataSetChanged()
    }

    class ChatItemViewHolder(view: View, private val providerGlide: IGlideProvider) :
        RecyclerView.ViewHolder(
            view
        ) {
        private val userPhoto: ImageView = view.findViewById(R.id.chat_user_photo)
        private val userName: TextView = view.findViewById(R.id.chat_user_name)
        private val userMessage: TextView = view.findViewById(R.id.chat_user_message)
        private val messageDate: TextView = view.findViewById(R.id.chat_date)
        private val photoDrawable = view.resources.getDrawable(R.mipmap.ic_launcher)

        fun bind(message: Message) {
            val dateFormatter: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")

            // userPhoto.setImageDrawable(photoDrawable)
            providerGlide.loadImage(message.userPhoto(), userPhoto, isCircle = true)
            userName.text = message.getUserName()
            userMessage.text = message.getMessage()
            messageDate.text = dateFormatter.format(Date(message.getDate() * dateMult))
        }
    }

    fun update(newList: List<Message>) {
        val messageDiffUtilCallback =
            RoomDiffUtilCallback(values, newList)
        DiffUtil.calculateDiff(messageDiffUtilCallback).dispatchUpdatesTo(this)
    }

    class RoomDiffUtilCallback(
        private val mOldList: List<Message>,
        private val mNewList: List<Message>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = mOldList.size

        override fun getNewListSize(): Int = mNewList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            mOldList[oldItemPosition] == mNewList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            mOldList[oldItemPosition].getId() == mNewList[newItemPosition].getId() &&
                mOldList[oldItemPosition].getMessage() == mNewList[newItemPosition].getMessage()
    }
}
