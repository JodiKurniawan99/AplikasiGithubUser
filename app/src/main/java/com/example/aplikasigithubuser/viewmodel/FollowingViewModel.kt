package com.example.aplikasigithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithubuser.model.UserGithubModel
import com.example.aplikasigithubuser.network.GithubApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _listReview = MutableLiveData<List<UserGithubModel>>()
    val listReview: LiveData<List<UserGithubModel>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isShowImage = MutableLiveData<Boolean>()
    val isShowImage: LiveData<Boolean> = _isShowImage

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        val call = GithubApiConfig.githubApiService.getUserFollowing(username)
        call.enqueue(object : Callback<List<UserGithubModel>> {
            override fun onResponse(

                call: Call<List<UserGithubModel>>, response: Response<List<UserGithubModel>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val list = response.body().orEmpty()
                    _listReview.postValue(list)
                    if (response.body()!!.isEmpty()){
                        _isShowImage.value = true
                    }
                }
            }

            override fun onFailure(call: Call<List<UserGithubModel>>, t: Throwable) {
                _isLoading.value = false
                _isShowImage.value = true
            }

        })
    }
}