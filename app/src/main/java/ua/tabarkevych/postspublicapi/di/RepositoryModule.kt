package ua.tabarkevych.postspublicapi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.tabarkevych.postspublicapi.network.PostApiService
import ua.tabarkevych.postspublicapi.network.model.PostNetworkMapper
import ua.tabarkevych.postspublicapi.repository.PostsRepository
import ua.tabarkevych.postspublicapi.room.post.PostDatabase
import ua.tabarkevych.postspublicapi.room.post.PostDatabaseMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePostsRepository(
        postApiService: PostApiService,
        postDatabase: PostDatabase,
        databaseMapper: PostDatabaseMapper,
        networkMapper: PostNetworkMapper
    ): PostsRepository {
        return PostsRepository(postApiService, postDatabase, databaseMapper, networkMapper)
    }
}