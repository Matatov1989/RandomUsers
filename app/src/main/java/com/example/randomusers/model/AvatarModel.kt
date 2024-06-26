package com.example.randomusers.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AvatarModel(
    @ColumnInfo(name = "large") val large: String,
    @ColumnInfo(name = "medium") val medium: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
) : Parcelable

