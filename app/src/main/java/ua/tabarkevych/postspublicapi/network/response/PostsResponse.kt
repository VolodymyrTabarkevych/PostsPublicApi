package ua.tabarkevych.postspublicapi.network.response

import com.squareup.moshi.Json
import ua.tabarkevych.postspublicapi.network.model.PostNetworkEntity

class PostsResponse(
    @Json(name = "meta") val meta: Meta,
    @Json(name = "data") val data: List<PostNetworkEntity>
) {
    class Meta(
        @Json(name = "pagination") val pagination: Pagination
    ) {
        class Pagination(
            @Json(name = "pages") val pages: Int,
            @Json(name = "page") val page: Int
        )
    }
}

