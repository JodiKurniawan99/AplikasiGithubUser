package com.example.aplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.databinding.UserBinding
import com.example.aplikasigithubuser.helper.UserGithubDiffCallback
import com.example.aplikasigithubuser.model.UserGithubModel

class FollowingUserAdapter(private val listFollowing: MutableList<UserGithubModel>): RecyclerView.Adapter<FollowingUserAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size

    fun setUserData(items: List<UserGithubModel>) {
        val diffCallback = UserGithubDiffCallback(listFollowing, items)
        val diffResult = diffCallback.let { DiffUtil.calculateDiff(it) }
        listFollowing.clear()
        listFollowing.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = UserBinding.bind(itemView)

        fun bind(dataFollowing: UserGithubModel) {
            Glide.with(itemView.context)
                .load(dataFollowing.avatar_url)
                .apply(RequestOptions().override(100, 100))
                .into(itemView.findViewById(R.id.avatar))
            binding.fullName.text = dataFollowing.login
            binding.username.text = dataFollowing.type
        }
    }
}