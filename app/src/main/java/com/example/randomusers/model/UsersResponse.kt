package com.example.randomusers.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("results") val usersList: MutableList<UserModel>
)
