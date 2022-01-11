package com.example.aplikasigithubuser.network

import com.example.aplikasigithubuser.model.UserDetailModel
import com.example.aplikasigithubuser.model.UserGithubModel
import retrofit2.Call
import retrofit2.http.*

interface GithubApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUserData(@Query("q") q: String): Call<GithubUserRespone>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailModel>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<UserGithubModel>>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<UserGithubModel>>
}