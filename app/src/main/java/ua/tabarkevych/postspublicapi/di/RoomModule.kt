package ua.tabarkevych.postspublicapi.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.tabarkevych.postspublicapi.room.post.PostDatabaseMapper
import ua.tabarkevych.postspublicapi.room.post.PostDao
import ua.tabarkevych.postspublicapi.room.post.PostDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providePostDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            PostDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDao(postDatabase: PostDatabase): PostDao {
        return postDatabase.postDao()
    }

    @Singleton
    @Provides
    fun providePostsMapper(): PostDatabaseMapper {
        return PostDatabaseMapper()
    }
}