package com.byron.data.model

data class ImageResponse(
    var total: Int,
    var totalPages: Int,
    var results: List<RemoteImage>
)