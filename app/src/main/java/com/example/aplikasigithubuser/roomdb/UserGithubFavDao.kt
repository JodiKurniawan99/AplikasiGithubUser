package com.example.aplikasigithubuser.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserGithubFavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(userFav: UserGithubFav)
    @Delete
    fun deleteUserData(userFav: UserGithubFav)
    @Query("SELECT * from usergithubfav ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<UserGithubFav>>







}