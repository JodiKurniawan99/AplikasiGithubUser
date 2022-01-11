package com.example.aplikasigithubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.adapter.UserAdapter
import com.example.aplikasigithubuser.databinding.ActivityListUserBinding
import com.example.aplikasigithubuser.viewmodel.ViewModelListUser


class ListUserActivity : AppCompatActivity() {
    private var _activityListUserBinding: ActivityListUserBinding? = null
    private val binding get() = _activityListUserBinding

    private lateinit var viewmodel: ViewModelListUser
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityListUserBinding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelListUser::class.java)
        recyclerViewConfigure()

        viewmodel.listReview.observe(this, {
            userAdapter.setUserListData(it)
        })

        viewmodel.isLoading.observe(this,{
            showLoadingProgress(it)
        })

        viewmodel.isShowImageView.observe(this,{
            showimageView(it)
        })

        viewmodel.isShowImageNotFound.observe(this,{
            showimageNotFound(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.cari_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewmodel.getListUser(query)
                binding?.ivHome?.visibility = View.GONE
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, SettingAppActivity::class.java)
                startActivity(i)
                true
            }
            R.id.menu2 -> {
                val i = Intent(this, ListUserFavoritActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun showLoadingProgress(state: Boolean) {
        if (state) {
            binding?.loadingProgress?.visibility = View.VISIBLE
        } else {
            binding?.loadingProgress?.visibility = View.INVISIBLE
        }
    }

    private fun showimageView(state: Boolean) {
        if (state) {
            binding?.ivHome?.visibility = View.VISIBLE
        } else {
            binding?.ivHome?.visibility = View.INVISIBLE
        }
    }

    private fun showimageNotFound(state: Boolean) {
        if (state) {
            binding?.ivNotFound?.visibility = View.VISIBLE
        } else {
            binding?.ivNotFound?.visibility = View.INVISIBLE
        }
    }

    private fun recyclerViewConfigure() {
        userAdapter = UserAdapter()
        binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?.setHasFixedSize(true)
        binding?.rvUsers?.adapter = userAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityListUserBinding = null
    }

    companion object {
        val TAG: String = ListUserActivity::class.java.simpleName
    }
}