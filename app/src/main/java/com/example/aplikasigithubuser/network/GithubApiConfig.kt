package com.example.aplikasigithubuser.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubApiConfig {
    companion object {
        private val retrofitConfig: Retrofit by lazy {
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/").build()
        }

        val githubApiService by lazy {
            val create: GithubApiService = retrofitConfig.create(GithubApiService::class.java)
            create
        }
    }
}