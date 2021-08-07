package ua.tabarkevych.postspublicapi.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.tabarkevych.postspublicapi.model.Post
import ua.tabarkevych.postspublicapi.network.PostApiService
import ua.tabarkevych.postspublicapi.network.model.PostNetworkMapper
import ua.tabarkevych.postspublicapi.network.response.PostsResponse
import ua.tabarkevych.postspublicapi.room.post.PostDatabase
import ua.tabarkevych.postspublicapi.room.post.PostDatabaseEntity
import ua.tabarkevych.postspublicapi.room.post.PostDatabaseMapper
import ua.tabarkevych.postspublicapi.util.Constants
import ua.tabarkevych.publicapiapp.ui.posts.DataState
import java.lang.Exception
import java.net.UnknownHostException

class PostsRepository(
    private val postApiService: PostApiService,
    private val postDatabase: PostDatabase,
    private val databaseMapper: PostDatabaseMapper,
    private val networkMapper: PostNetworkMapper
) {
    suspend fun getPosts(page: Int): Flow<DataState<List<Post>>> = flow {
        if (page == Constants.FIRST_PAGE) emit(DataState.Loading) else emit(DataState.NextPostsLoading)
        try {
            val networkPosts: PostsResponse = postApiService.getPosts(page)
            val posts: List<Post> = networkMapper.toDomainList(networkPosts.data)
            emit(DataState.IsLastPage(page == networkPosts.meta.pagination.pages))

            //Insert or update received posts to database. Add page number to each post.
            posts.forEach { post ->
                postDatabase.postDao().insert(
                    databaseMapper.mapFromDomainModel(post)
                        .also {
                            it.page = networkPosts.meta.pagination.page
                            println("${it.id} --- ${it.page}")
                        })
            }

            //If data was received from server and saved to database -> increase page count
            val databasePosts: List<PostDatabaseEntity> =
                postDatabase.postDao().getPostsByPages(page)
            emit(DataState.IncreasePage)
            emit(DataState.PostsLoaded(databaseMapper.toDomainList(databasePosts)))
        } catch (e: UnknownHostException) {
            //If server doesn't respond -> get all posts from database until current page.
            val databasePosts: List<PostDatabaseEntity> =
                postDatabase.postDao().getPostsByPages(page)
            //If posts from last page exits in database -> increase page number for next network request
            databasePosts.find { it.page == page }?.run { emit(DataState.IncreasePage) }
            emit(DataState.PostsLoaded(databaseMapper.toDomainList(databasePosts)))
        } catch (e: Exception) {
        }
    }
}