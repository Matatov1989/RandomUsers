package com.example.randomusers.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randomusers.R
import com.example.randomusers.model.UserModel

class UserListAdapter(
    private val users: MutableList<UserModel>,
    private val onItemClick: (UserModel) -> Unit,
    private val onItemLongClick: (String) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UsersListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user, parent, false)
        return UsersListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        Glide
            .with(holder.itemView)
            .load(users[position].imageUrl.large)
            .centerCrop()
            .placeholder(R.drawable.ic_android)
            .into(holder.imageViewAvatar)

        val fullName = "${users[position].name.firstName} ${users[position].name.lastName}"

        holder.textViewFullName.text = fullName
        holder.textViewMail.text = users[position].email

        holder.itemView.setOnClickListener {
            onItemClick.invoke(users[position])
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick.invoke(users[position].email)
            true
        }
    }

    override fun getItemCount(): Int = users.size

    class UsersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewAvatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
        val textViewFullName: TextView = itemView.findViewById(R.id.textViewFullName)
        val textViewMail: TextView = itemView.findViewById(R.id.textViewMail)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortListByAge(isAscending: Boolean) {
        if (isAscending)
            users.sortBy { it.dob.age }
        else
            users.sortByDescending { it.dob.age }
        notifyDataSetChanged()
    }
}