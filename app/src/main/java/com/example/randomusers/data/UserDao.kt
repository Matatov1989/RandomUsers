package com.example.randomusers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomusers.model.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM UserTable")
    suspend fun getUsers(): MutableList<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersList(list: List<UserModel>)

    @Query("DELETE FROM UserTable")
    suspend fun removeAllUsers()
}