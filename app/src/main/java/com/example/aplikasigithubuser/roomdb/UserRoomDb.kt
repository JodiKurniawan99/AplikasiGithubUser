package com.example.aplikasigithubuser.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserGithubFav::class], version = 3)
abstract class UserRoomDb: RoomDatabase() {
    abstract fun userFavDao(): UserGithubFavDao
    companion object {
        @Volatile
        private var INSTANCE: UserRoomDb? = null
        @JvmStatic
        fun getDatabase(context: Context): UserRoomDb {
            if (INSTANCE == null) {
                synchronized(UserRoomDb::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserRoomDb::class.java, "user_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as UserRoomDb
        }
    }
}