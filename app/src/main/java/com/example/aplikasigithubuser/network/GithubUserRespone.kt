package com.example.aplikasigithubuser.network

import android.os.Parcelable
import com.example.aplikasigithubuser.model.UserGithubModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserRespone(
    val total_count : String,
    val incomplete_results: Boolean? = null,
    val items : List<UserGithubModel>
): Parcelable