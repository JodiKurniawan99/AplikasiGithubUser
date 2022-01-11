package com.example.aplikasigithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.adapter.UserFavoriteAdapter
import com.example.aplikasigithubuser.databinding.ActivityListUserFavoritBinding
import com.example.aplikasigithubuser.helper.ViewModelUserFactory
import com.example.aplikasigithubuser.viewmodel.ViewModelListUserFav

class ListUserFavoritActivity : AppCompatActivity() {

    private var _activityListUserBinding: ActivityListUserFavoritBinding? = null
    private val binding get() = _activityListUserBinding
    private lateinit var adapter: UserFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user_favorit)

        val mainViewModel = obtainViewModel(this)
        mainViewModel.getAllFavoriteUser().observe(this, { userFavList ->
            if (userFavList != null) {
                adapter.setListNotes(userFavList)
            }
        })

        supportActionBar?.title = resources.getString(R.string.app_list_fav)
        _activityListUserBinding = ActivityListUserFavoritBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = UserFavoriteAdapter()
        binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?.setHasFixedSize(true)
        binding?.rvUsers?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityListUserBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): ViewModelListUserFav {
        val factory = ViewModelUserFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ViewModelListUserFav::class.java)
    }
}