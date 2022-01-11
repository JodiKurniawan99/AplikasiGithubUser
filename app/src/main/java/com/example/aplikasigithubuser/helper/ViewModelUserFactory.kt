package com.example.aplikasigithubuser.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.viewmodel.UserFavAddViewModel
import com.example.aplikasigithubuser.viewmodel.ViewModelListUserFav

class ViewModelUserFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelUserFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelUserFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelUserFactory::class.java) {
                    INSTANCE = ViewModelUserFactory(application)
                }
            }
            return INSTANCE as ViewModelUserFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelListUserFav::class.java)) {
            return ViewModelListUserFav(mApplication) as T
        } else if (modelClass.isAssignableFrom(UserFavAddViewModel::class.java)) {
            return UserFavAddViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}