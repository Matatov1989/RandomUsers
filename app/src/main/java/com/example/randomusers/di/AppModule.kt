package com.example.randomusers.di

import android.content.Context
import androidx.room.Room
import com.example.randomusers.data.UserDao
import com.example.randomusers.data.UserDatabase
import com.example.randomusers.network.RandomUserApi
import com.example.randomusers.util.Constants.RANDOM_USER_BASE_URL
import com.example.randomusers.util.Constants.USER_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): RandomUserApi {
        return Retrofit.Builder()
            .baseUrl(RANDOM_USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserApi::class.java)
    }


    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao = userDatabase.userDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            USER_DB
        ).fallbackToDestructiveMigration().build()
}