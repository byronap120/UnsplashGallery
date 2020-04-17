package com.byron.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Urls(
    var full: String,
    var regular: String
) : Parcelable