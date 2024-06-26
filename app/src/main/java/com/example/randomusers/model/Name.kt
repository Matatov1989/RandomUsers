package com.example.randomusers.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Name(
    @SerializedName("first") @ColumnInfo(name = "firstName") val firstName: String,
    @SerializedName("last") @ColumnInfo(name = "lastName") val lastName: String
) : Parcelable
