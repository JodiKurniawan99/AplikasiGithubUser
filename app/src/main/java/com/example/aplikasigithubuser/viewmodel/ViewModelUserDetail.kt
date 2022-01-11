package com.example.aplikasigithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithubuser.model.UserDetailModel
import com.example.aplikasigithubuser.network.GithubApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUserDetail: ViewModel() {

    private val _listReview = MutableLiveData<UserDetailModel>()
    val listReview: LiveData<UserDetailModel> = _listReview

    fun setUserData(username: String) {
        val service = GithubApiConfig.githubApiService.getUserDetail(username)
        service.enqueue(object : Callback<UserDetailModel> {
            override fun onResponse(
                call: Call<UserDetailModel>?,
                response: Response<UserDetailModel>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) _listReview.postValue(user!!)
                }
            }
            override fun onFailure(call: Call<UserDetailModel>?, t: Throwable?) {
            }
        })
    }
}