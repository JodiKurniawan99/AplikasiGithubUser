package com.example.aplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithubuser.ui.ListUserActivity.Companion.TAG
import com.example.aplikasigithubuser.model.UserGithubModel
import com.example.aplikasigithubuser.network.GithubApiConfig
import com.example.aplikasigithubuser.network.GithubUserRespone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelListUser: ViewModel() {
    private val _listReview = MutableLiveData<List<UserGithubModel>>()
    val listReview: LiveData<List<UserGithubModel>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isShowImageView = MutableLiveData<Boolean>()
    val isShowImageView: LiveData<Boolean> = _isShowImageView

    private val _isShowImageNotFound = MutableLiveData<Boolean>()
    val isShowImageNotFound: LiveData<Boolean> = _isShowImageNotFound

    fun getListUser(query: String) {
        _isLoading.value = true
        val client = GithubApiConfig.githubApiService.searchUserData(query)
        client.enqueue(object : Callback<GithubUserRespone> {
            override fun onResponse(call: Call<GithubUserRespone>, response: Response<GithubUserRespone>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isShowImageView.value = false
                    _isShowImageNotFound.value = false
                    val data: List<UserGithubModel> = response.body()?.items!!
                    _listReview.postValue(data)

                    if (data.isEmpty()){
                        _listReview.postValue(data)
                        _isShowImageNotFound.value = true
                    }

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubUserRespone>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}