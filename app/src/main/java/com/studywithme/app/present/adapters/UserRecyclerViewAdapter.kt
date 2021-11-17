package com.studywithme.app.present.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.studywithme.app.R
import com.studywithme.app.databinding.FragmentMembersUserItemBinding
import com.studywithme.app.objects.user.User

class UserRecyclerViewAdapter(
    val values: MutableList<User>,
    private val listener: OnUserClickListener
) :
    RecyclerView.Adapter<UserRecyclerViewAdapter.UserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_members_user_item, parent, false)
        Log.d("UserList", "onCreateViewHolder")
        return UserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(values[position], listener, position)
    }

    override fun getItemCount(): Int = values.size

    class UserItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = FragmentMembersUserItemBinding.bind(view)

        fun bind(user: User, listener: OnUserClickListener, position: Int) = with(binding) {
            Log.d("UsersList", "name: " + user.getName())
            userName.text = user.getName()
            itemView.setOnClickListener {
                listener.onUserClick(position)
            }
            // userPhoto.setImageResource(user.photo.toInt())
        }
    }

    interface OnUserClickListener {
        fun onUserClick(position: Int)
    }

    fun update(newList: List<User>) {
        val messageDiffUtilCallback =
            UserDiffUtilCallback(values, newList)
        DiffUtil.calculateDiff(messageDiffUtilCallback).dispatchUpdatesTo(this)
    }

    class UserDiffUtilCallback(
        private val mOldList: List<User>,
        private val mNewList: List<User>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = mOldList.size

        override fun getNewListSize(): Int = mNewList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            mOldList[oldItemPosition] == mNewList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            mOldList[oldItemPosition].getId() == mNewList[newItemPosition].getId() &&
                mOldList[oldItemPosition].getName() == mNewList[newItemPosition].getName()
    }
}
