package com.example.randomusers.network

import com.example.randomusers.model.UsersResponse
import com.example.randomusers.util.Constants.GET_USERS
import com.example.randomusers.util.Constants.NUMBER_OF_USERS
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET(GET_USERS)
    suspend fun getUsersData(
        @Query("results") numberOfUsers: Int = NUMBER_OF_USERS
    ): Response<UsersResponse>

    @GET("test")
    fun getUserDataTest(
        @Query("id") id: Int = 1
    ): Call<UsersResponse>
}