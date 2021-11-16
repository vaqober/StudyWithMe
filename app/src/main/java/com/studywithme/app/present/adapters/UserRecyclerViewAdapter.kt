package com.studywithme.app.present.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studywithme.app.R
import com.studywithme.app.databinding.FragmentMembersUserItemBinding
import com.studywithme.app.objects.user.User

class UserRecyclerViewAdapter(
    val values: MutableList<User>,
    private val listener: OnUserClickListener
) : RecyclerView.Adapter<UserRecyclerViewAdapter.UserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_members_user_item, parent, false)
        return UserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(values[position], listener, position)
    }

    override fun getItemCount(): Int = values.size

    class UserItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = FragmentMembersUserItemBinding.bind(view)

        fun bind(user: User, listener: OnUserClickListener, position: Int) = with(binding) {
            Log.d("UsersList", user.toString())
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
}
