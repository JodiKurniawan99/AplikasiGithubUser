package com.example.aplikasigithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.ui.DetailUserActivity
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.helper.UserGithubDiffCallback
import com.example.aplikasigithubuser.model.UserGithubModel


class UserAdapter(
    private val listUser: MutableList<UserGithubModel>? = mutableListOf()
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var itemView: View

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUser: ImageView = itemView.findViewById(R.id.avatar)
        val usernameUser: TextView = itemView.findViewById(R.id.username)
        val nameUser: TextView = itemView.findViewById(R.id.fullName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int = listUser?.size ?: 0

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userItem = listUser?.get(position)
        holder.nameUser.text = userItem?.login
        holder.usernameUser.text = userItem?.type
        Glide.with(itemView.context).load(userItem?.avatar_url).circleCrop().into(holder.imageUser)

        holder.itemView.setOnClickListener {
            val dataUserIntent = UserGithubModel(
                userItem?.login!!,
                userItem.avatar_url,
                userItem.type
            )
            val intentDetailUser = Intent(it.context, DetailUserActivity::class.java)
            intentDetailUser.putExtra(DetailUserActivity.EXTRA_DETAIL_USER, dataUserIntent)
            it.context.startActivity(intentDetailUser)
        }
    }

    fun setUserListData(listUsersGithub: List<UserGithubModel>?) {
        if (listUsersGithub != null) {
            val diffCallback = listUser?.let { UserGithubDiffCallback(it, listUsersGithub) }
            val diffResult = diffCallback?.let { DiffUtil.calculateDiff(it) }
            listUser?.clear()
            listUser?.addAll(listUsersGithub)
            diffResult?.dispatchUpdatesTo(this)
        }
    }
}