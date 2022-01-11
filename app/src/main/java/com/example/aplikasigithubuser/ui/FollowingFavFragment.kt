package com.example.aplikasigithubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.adapter.FollowingUserAdapter
import com.example.aplikasigithubuser.databinding.FragmentFollowingFavBinding
import com.example.aplikasigithubuser.model.UserGithubModel
import com.example.aplikasigithubuser.roomdb.UserGithubFav
import com.example.aplikasigithubuser.viewmodel.FollowingViewModel

class FollowingFavFragment : Fragment() {

    private val listDataFollowing: MutableList<UserGithubModel> = arrayListOf()
    private lateinit var followingUserAdapter: FollowingUserAdapter
    private lateinit var followingModelView: FollowingViewModel
    private lateinit var followingBinding: FragmentFollowingFavBinding

    private var userGithubFav: UserGithubFav? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followingBinding = FragmentFollowingFavBinding.inflate(inflater,container,false)
        return followingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingUserAdapter = FollowingUserAdapter(listDataFollowing)
        followingModelView = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        userGithubFav = activity?.intent?.getParcelableExtra(EXTRA_USERFAV)
        rvListConfiguration()

        userGithubFav?.name?.let { followingModelView.getUserFollowing(it) }

        followingModelView.listReview.observe(viewLifecycleOwner,{
            followingUserAdapter.setUserData(it)
        })

        followingModelView.isLoading.observe(viewLifecycleOwner,{
            showLoadingProgress(it)
        })

        followingModelView.isShowImage.observe(viewLifecycleOwner,{
            showimageView(it)
        })
    }

    private fun rvListConfiguration() {
        followingBinding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        followingBinding.rvFollowing.setHasFixedSize(true)
        followingBinding.rvFollowing.adapter = followingUserAdapter
        followingBinding.rvFollowing.isNestedScrollingEnabled = false
    }

    private fun showLoadingProgress(state: Boolean) {
        if (state) {
            followingBinding.pbFollowing.visibility = View.VISIBLE
        } else {
            followingBinding.pbFollowing.visibility = View.INVISIBLE
        }
    }

    private fun showimageView(state: Boolean) {
        if (state) {
            followingBinding.ivCannotFound.visibility = View.VISIBLE
        } else {
            followingBinding.ivCannotFound.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val EXTRA_USERFAV = "extra_userfav"
    }
}