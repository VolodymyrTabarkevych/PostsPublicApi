package ua.tabarkevych.postspublicapi.room.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postEntity: PostDatabaseEntity): Long

    @Query("SELECT * FROM posts")
    suspend fun getAll(): List<PostDatabaseEntity>

    @Query("SELECT * FROM posts WHERE id == :postId")
    suspend fun getById(postId: Int): PostDatabaseEntity

    @Query("SELECT * FROM posts WHERE page <= :pageCount")
    suspend fun getPostsByPages(pageCount: Int): List<PostDatabaseEntity>
}