package com.example.aplikasigithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.aplikasigithubuser.repository.UserRepository
import com.example.aplikasigithubuser.roomdb.UserGithubFav

class UserFavAddViewModel(application: Application) : ViewModel() {
    private val mUserFavRepository: UserRepository = UserRepository(application)

    fun insertUserData(userGithubFav: UserGithubFav) {
        mUserFavRepository.insertUserData(userGithubFav)
    }

    fun deleteUserData(userGithubFav: UserGithubFav) {
        mUserFavRepository.deleteUserData(userGithubFav)
    }

}