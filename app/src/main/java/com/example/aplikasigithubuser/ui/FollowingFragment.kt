package com.example.aplikasigithubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.adapter.FollowingUserAdapter
import com.example.aplikasigithubuser.databinding.FragmentFollowingBinding
import com.example.aplikasigithubuser.model.UserGithubModel
import com.example.aplikasigithubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private val listDataFollowing: MutableList<UserGithubModel> = arrayListOf()
    private lateinit var followingUserAdapter: FollowingUserAdapter
    private lateinit var followingModelView: FollowingViewModel
    private lateinit var followingBinding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followingBinding = FragmentFollowingBinding.inflate(inflater,container,false)
        return followingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingUserAdapter = FollowingUserAdapter(listDataFollowing)
        followingModelView = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val dataUser = activity?.intent?.getParcelableExtra<UserGithubModel>(EXTRA_DETAIL_USER) as UserGithubModel
        rvListConfiguration()

        followingModelView.getUserFollowing(dataUser.login)
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
        const val EXTRA_DETAIL_USER = "extra_detail_user"
    }
}