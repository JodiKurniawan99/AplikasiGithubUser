package com.example.aplikasigithubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserGithubModel(
    val login: String,
    val avatar_url: String,
    val type: String
): Parcelable