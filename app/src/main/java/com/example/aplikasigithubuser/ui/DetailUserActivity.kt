package com.example.aplikasigithubuser.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasigithubuser.adapter.DetailUserAdapter
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.databinding.ActivityDetailUserBinding
import com.example.aplikasigithubuser.helper.ViewModelUserFactory
import com.example.aplikasigithubuser.model.UserGithubModel
import com.example.aplikasigithubuser.roomdb.UserGithubFav
import com.example.aplikasigithubuser.viewmodel.UserFavAddViewModel
import com.example.aplikasigithubuser.viewmodel.ViewModelUserDetail
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private var _activityDetailUserBinding: ActivityDetailUserBinding? = null
    private val binding get() = _activityDetailUserBinding
    private lateinit var viewmodel: ViewModelUserDetail
    private var userFav: UserGithubFav? = null
    private lateinit var userFavAddViewModel: UserFavAddViewModel
    private lateinit var avatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        userFavAddViewModel = obtainViewModel(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.viewpager?.layoutParams?.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            binding?.viewpager?.layoutParams?.height = resources.getDimension(R.dimen.height1).toInt()
        }

        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelUserDetail::class.java)

        val userModel = intent.getParcelableExtra<UserGithubModel>(EXTRA_DETAIL_USER)

        viewmodel.setUserData(userModel!!.login)
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
        userFav = UserGithubFav()

        binding?.fabAdd?.setOnClickListener{
            if (it.id == R.id.fab_add){
                userFav.let {
                        userFav?.name = binding?.username?.text.toString().trim()
                        userFav?.username = supportActionBar?.title.toString().trim()
                        userFav?.avatar = avatar
                }
                userFavAddViewModel.insertUserData(userFav as UserGithubFav)
            }
        }
        configurationViewPager()
    }

    private fun configurationViewPager() {
        val sectionsPagerAdapter = DetailUserAdapter(this)
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

    private fun obtainViewModel(activity: AppCompatActivity): UserFavAddViewModel {
        val factory = ViewModelUserFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserFavAddViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailUserBinding = null
    }

    companion object {
        const val EXTRA_DETAIL_USER = "extra_detail_user"
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}
