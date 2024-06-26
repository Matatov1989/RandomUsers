package com.example.randomusers.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "UserTable")
@Parcelize
data class UserModel(
    @PrimaryKey
    @SerializedName("email") @ColumnInfo(name = "email") val email: String,
    @Embedded @SerializedName("name") val name: Name,
    @Embedded @SerializedName("dob") val dob: DateOfBirthday,
    @Embedded @SerializedName("picture") val imageUrl: AvatarModel
) : Parcelable
