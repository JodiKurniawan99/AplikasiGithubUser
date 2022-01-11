package com.example.aplikasigithubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aplikasigithubuser.ui.FollowerFavFragment
import com.example.aplikasigithubuser.ui.FollowingFavFragment

class DetailUserFavAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFavFragment()
            1 -> fragment = FollowingFavFragment()
        }
        return fragment as Fragment
    }
}