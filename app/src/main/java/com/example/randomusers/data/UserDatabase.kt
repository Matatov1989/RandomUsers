package com.example.randomusers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomusers.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}