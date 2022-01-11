package com.example.aplikasigithubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.aplikasigithubuser.roomdb.UserGithubFav

class UserFavDiffCallback(private val oldUserList: ArrayList<UserGithubFav>, private val newUserList: List<UserGithubFav>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldUserList.size
    }

    override fun getNewListSize(): Int {
        return newUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUserList[oldItemPosition].id == newUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldUserList[oldItemPosition]
        val newUser = newUserList[newItemPosition]
        return oldUser.name == newUser.name && oldUser.username == newUser.username
    }
}