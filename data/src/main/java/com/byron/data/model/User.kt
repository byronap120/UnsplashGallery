package com.byron.data.model

import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var username: String,
    var name: String,
    @Embedded @SerializedName("profile_image") var profileImage: ProfileImage
): Parcelable