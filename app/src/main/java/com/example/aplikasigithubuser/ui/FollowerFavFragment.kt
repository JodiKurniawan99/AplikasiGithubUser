package com.example.aplikasigithubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.adapter.FollowerUserAdapter
import com.example.aplikasigithubuser.databinding.FragmentFollowerFavBinding
import com.example.aplikasigithubuser.model.UserGithubModel
import com.example.aplikasigithubuser.roomdb.UserGithubFav
import com.example.aplikasigithubuser.viewmodel.FollowerViewModel

class FollowerFavFragment : Fragment() {

    private val listData: MutableList<UserGithubModel> = ArrayList()
    private lateinit var followerUserAdapter: FollowerUserAdapter
    private lateinit var followerModelView: FollowerViewModel
    private lateinit var followerBinding: FragmentFollowerFavBinding

    private var userGithubFav: UserGithubFav? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followerBinding = FragmentFollowerFavBinding.inflate(inflater,container,false)
        return followerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followerUserAdapter = FollowerUserAdapter(listData)
        followerModelView = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)

        userGithubFav = activity?.intent?.getParcelableExtra(EXTRA_USERFAV)
        rvListConfiguration()

        userGithubFav?.name?.let { followerModelView.getUserFollower(it) }

        followerModelView.listReview.observe(viewLifecycleOwner,{
            followerUserAdapter.setUserData(it)
        })

        followerModelView.isLoading.observe(viewLifecycleOwner,{
            showLoadingProgress(it)
        })

        followerModelView.isShowImage.observe(viewLifecycleOwner,{
            showimageView(it)
        })
    }

    private fun rvListConfiguration() {
        followerBinding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        followerBinding.rvFollowers.setHasFixedSize(true)
        followerBinding.rvFollowers.adapter = followerUserAdapter
        followerBinding.rvFollowers.isNestedScrollingEnabled = false
    }

    private fun showLoadingProgress(state: Boolean) {
        if (state) {
            followerBinding.pbFollowers.visibility = View.VISIBLE
        } else {
            followerBinding.pbFollowers.visibility = View.INVISIBLE
        }
    }

    private fun showimageView(state: Boolean) {
        if (state) {
            followerBinding.ivCannotFound.visibility = View.VISIBLE
        } else {
            followerBinding.ivCannotFound.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val EXTRA_USERFAV = "extra_userfav"
    }
}