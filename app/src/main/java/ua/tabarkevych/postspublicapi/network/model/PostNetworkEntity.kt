package ua.tabarkevych.postspublicapi.network.model

import com.squareup.moshi.Json

class PostNetworkEntity(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "body") val body: String
)