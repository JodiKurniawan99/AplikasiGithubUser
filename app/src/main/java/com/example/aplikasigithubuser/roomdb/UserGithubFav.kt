package com.example.aplikasigithubuser.roomdb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["name"], unique = true)])
@Parcelize

data class UserGithubFav(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "username")
    var username: String? = null,
    @ColumnInfo(name = "avatar")
    var avatar: String? = null
):Parcelable