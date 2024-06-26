package com.example.randomusers.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateOfBirthday(
    @SerializedName("date") @ColumnInfo(name = "date") val date: String,
    @SerializedName("age") @ColumnInfo(name = "age") val age: Int
) : Parcelable
