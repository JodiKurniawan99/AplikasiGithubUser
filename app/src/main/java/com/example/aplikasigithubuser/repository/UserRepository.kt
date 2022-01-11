package com.example.aplikasigithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.aplikasigithubuser.roomdb.UserGithubFav
import com.example.aplikasigithubuser.roomdb.UserGithubFavDao
import com.example.aplikasigithubuser.roomdb.UserRoomDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserFavDao: UserGithubFavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDb.getDatabase(application)
        mUserFavDao = db.userFavDao()
    }

    fun getAllFavoriteUser(): LiveData<List<UserGithubFav>> = mUserFavDao.getAllFavoriteUser()

    fun insertUserData(note: UserGithubFav) {
        executorService.execute { mUserFavDao.insertUserData(note) }
    }

    fun deleteUserData(note: UserGithubFav) {
        executorService.execute { mUserFavDao.deleteUserData(note) }
    }
}