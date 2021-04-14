package project.dheeraj.cinewatch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.MovieDB
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.CONSTANTS.Companion.TABLE_NAME

/**
 * Created by Dheeraj Kotwani on 11-04-2021.
 */

/**
 * Data Access Object[DAO]
 */
@Dao
interface BookmarkDao {

    /**
     * Insert [Movie] into the Movie Table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(Movie: MovieDB)

    /**
     * Delete [Movie] by [Movie.id]
     */
    @Query("Delete from ${CONSTANTS.TABLE_NAME} where id=:id")
    fun deleteMovieById(id : Int)

}