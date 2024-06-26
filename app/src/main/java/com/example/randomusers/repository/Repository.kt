package com.example.randomusers.repository

import com.example.randomusers.data.UserDao
import com.example.randomusers.model.UserModel
import com.example.randomusers.model.UsersResponse
import com.example.randomusers.network.RandomUserApi
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: RandomUserApi, private val userDao: UserDao) {

    suspend fun fetchCachedUsers(): MutableList<UserModel> = userDao.getUsers()

    suspend fun getUsersData(): Response<UsersResponse> = api.getUsersData()

    suspend fun replaceData(list: List<UserModel>) {
        userDao.removeAllUsers()
        userDao.insertUsersList(list)
    }
}