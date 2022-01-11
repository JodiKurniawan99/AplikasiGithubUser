package com.example.aplikasigithubuser.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.databinding.UserBinding
import com.example.aplikasigithubuser.helper.UserFavDiffCallback
import com.example.aplikasigithubuser.roomdb.UserGithubFav
import com.example.aplikasigithubuser.ui.UserDetailFavActivity

class UserFavoriteAdapter : RecyclerView.Adapter<UserFavoriteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(private val binding: UserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: UserGithubFav) {
            with(binding) {
                fullName.text = note.name
                username.text = note.username
                Glide.with(itemView.context).load(note.avatar).circleCrop().into(avatar)
                cardView.setOnClickListener {
                    val intent = Intent(it.context, UserDetailFavActivity::class.java)
                    intent.putExtra(UserDetailFavActivity.EXTRA_USERFAV, note)
                    it.context.startActivity(intent)
                    (it.context as Activity).finish()
                }
            }
        }
    }

    private val listUsersFavorit = ArrayList<UserGithubFav>()
    fun setListNotes(listUsersFavorit: List<UserGithubFav>) {
        val diffCallback = UserFavDiffCallback(this.listUsersFavorit, listUsersFavorit)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUsersFavorit.clear()
        this.listUsersFavorit.addAll(listUsersFavorit)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = UserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listUsersFavorit[position])
    }

    override fun getItemCount(): Int = listUsersFavorit.size


}