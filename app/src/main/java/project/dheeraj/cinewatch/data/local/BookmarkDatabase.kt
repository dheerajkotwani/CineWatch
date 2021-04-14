package project.dheeraj.cinewatch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import project.dheeraj.cinewatch.data.local.dao.BookmarkDao
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.MovieDB

/**
 * Created by Dheeraj Kotwani on 11-04-2021.
 */
@Database(
    entities = [MovieDB::class],
    version = 1
)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun getBookmarkDao() : BookmarkDao

    companion object {

        const val DATABASE_NAME = "BOOKMARK_DATABASE"

        @Volatile
        private var INSTANCE : BookmarkDatabase? = null

        fun getInstance(context : Context) : BookmarkDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }

    }

}