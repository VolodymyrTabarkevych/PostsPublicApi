package ua.tabarkevych.postspublicapi.network

import retrofit2.http.GET
import retrofit2.http.Query
import ua.tabarkevych.postspublicapi.network.response.PostsResponse

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(
        @Query("page") page: Int
    ): PostsResponse
}