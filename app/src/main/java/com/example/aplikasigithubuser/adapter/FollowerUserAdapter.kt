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

class FollowerUserAdapter(private val listFollower: MutableList<UserGithubModel>):RecyclerView.Adapter<FollowerUserAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollower[position])
    }

    override fun getItemCount(): Int = listFollower.size


    fun setUserData(items: List<UserGithubModel>) {
        val diffCallback = UserGithubDiffCallback(listFollower, items)
        val diffResult = diffCallback.let { DiffUtil.calculateDiff(it) }
        listFollower.clear()
        listFollower.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = UserBinding.bind(itemView)

        fun bind(dataFollowers: UserGithubModel) {
            Glide.with(itemView.context)
                .load(dataFollowers.avatar_url)
                .apply(RequestOptions().override(100, 100))
                .into(itemView.findViewById(R.id.avatar))
            binding.fullName.text = dataFollowers.login
            binding.username.text = dataFollowers.type
        }
    }

}