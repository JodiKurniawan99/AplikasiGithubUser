package com.example.aplikasigithubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.aplikasigithubuser.model.UserGithubModel

class UserGithubDiffCallback(private val oldUserList: MutableList<UserGithubModel>, private val newUserList: List<UserGithubModel>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldUserList.size
    }

    override fun getNewListSize(): Int {
        return newUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUserList[oldItemPosition].login == newUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldUserList[oldItemPosition]
        val newUser = newUserList[newItemPosition]
        return oldUser.login == newUser.login && oldUser.avatar_url == newUser.avatar_url
    }
}