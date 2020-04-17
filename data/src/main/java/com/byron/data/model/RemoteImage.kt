package com.byron.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "favorite_images",
    indices = [Index("id")],
    primaryKeys = ["id"]
)

@Parcelize
data class RemoteImage(
    var id: String,
    var description: String?,
    var likes: Int,
    @SerializedName("alt_description") var altDescription: String?,
    @Embedded var user: User,
    @Embedded var urls: Urls
) : Parcelable

