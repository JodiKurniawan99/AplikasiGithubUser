package com.example.aplikasigithubuser.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.adapter.DetailUserFavAdapter
import com.example.aplikasigithubuser.databinding.ActivityUserDetailFavBinding
import com.example.aplikasigithubuser.helper.ViewModelUserFactory
import com.example.aplikasigithubuser.roomdb.UserGithubFav
import com.example.aplikasigithubuser.viewmodel.UserFavAddViewModel
import com.example.aplikasigithubuser.viewmodel.ViewModelUserDetail
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailFavActivity : AppCompatActivity() {

    private var userGithubFav: UserGithubFav? = null
    private lateinit var userFavAddViewModel: UserFavAddViewModel
    private lateinit var viewmodel: ViewModelUserDetail
    private lateinit var avatar: String
    private var _activityUserDetalFavBind: ActivityUserDetailFavBinding? = null
    private val binding get() = _activityUserDetalFavBind

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityUserDetalFavBind = ActivityUserDetailFavBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.viewpager?.layoutParams?.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            binding?.viewpager?.layoutParams?.height = resources.getDimension(R.dimen.height1).toInt()
        }

        userFavAddViewModel = obtainViewModel(this)
        userGithubFav = intent.getParcelableExtra(EXTRA_USERFAV)
        supportActionBar?.title = userGithubFav?.name
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelUserDetail::class.java)

        userGithubFav?.name?.let { viewmodel.setUserData(it) }
        viewmodel.listReview.observe(this, {
            supportActionBar?.title = it.name ?: resources.getString(R.string.username_github)
            binding?.username?.text = it.login
            binding?.following?.text = it.following.toString()
            binding?.followers?.text = it.followers.toString()
            binding?.repositories?.text = it.publicRepos.toString()
            binding?.location?.text = it.location ?: resources.getString(R.string.lokasi)
            binding?.company?.text = it.company ?: resources.getString(R.string.company)
            avatar = it.avatarUrl
            binding?.let { it1 ->
                Glide.with(this)
                    .load(it.avatarUrl)
                    .apply(RequestOptions().override(100, 100))
                    .into(it1.avatars)
            }
        })

        binding?.fabAdd?.setOnClickListener{
            if (it.id == R.id.fab_add){
                userFavAddViewModel.deleteUserData(userGithubFav as UserGithubFav)
                val i = Intent(this,ListUserFavoritActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        configurationViewPager()
    }

    private fun configurationViewPager() {
        val sectionsPagerAdapter = DetailUserFavAdapter(this)
        binding?.viewpager?.adapter = sectionsPagerAdapter
        binding?.tabs?.let {
            binding?.viewpager?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
        supportActionBar?.elevation = 0f
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityUserDetalFavBind = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserFavAddViewModel {
        val factory = ViewModelUserFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserFavAddViewModel::class.java)
    }

    companion object {
        const val EXTRA_USERFAV = "extra_userfav"
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}