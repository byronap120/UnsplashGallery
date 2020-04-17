package com.byron.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileImage(
    var medium: String,
    var large: String
): Parcelable