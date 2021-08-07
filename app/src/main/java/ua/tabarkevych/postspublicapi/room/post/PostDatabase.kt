package ua.tabarkevych.postspublicapi.room.post

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostDatabaseEntity::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        val DATABASE_NAME: String = "post_db"
    }
}