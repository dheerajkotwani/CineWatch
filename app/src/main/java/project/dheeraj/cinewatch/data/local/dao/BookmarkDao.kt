package project.dheeraj.cinewatch.data.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
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
     * Delete movie from Database
     */
    @Delete
    suspend fun removeMovie(movie: MovieDB)

    /**
     * Delete [Movie] by [Movie.id]
     */
    @Query("Delete from ${TABLE_NAME} where id=:id")
    suspend fun deleteMovieById(id : Int)

    /**
     * Fetch all movies
     */
    @Query("Select * from $TABLE_NAME")
    fun getAllBookmark() : List<MovieDB>

    /**
     * Check movie exist in DB
     */
    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_NAME WHERE id = :id)")
    fun bookmarkExist(id: Int): Boolean


}