package com.example.aplikasigithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithubuser.repository.UserRepository
import com.example.aplikasigithubuser.roomdb.UserGithubFav

class ViewModelListUserFav(application: Application) : ViewModel() {
    private val mUserFavRepository: UserRepository = UserRepository(application)
    fun getAllFavoriteUser(): LiveData<List<UserGithubFav>> = mUserFavRepository.getAllFavoriteUser()
}