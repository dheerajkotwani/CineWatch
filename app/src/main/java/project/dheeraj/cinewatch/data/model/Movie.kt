package project.dheeraj.cinewatch.data.model

import androidx.room.Entity
import org.json.JSONObject
import project.dheeraj.cinewatch.data.model.Movie.Companion.TABLE_NAME

/**
 * Created by Dheeraj Kotwani on 19-03-2021.
 */

@Entity(tableName = TABLE_NAME)
data class Movie (
    val id: Int,
    val vote_count: Int,
    val runtime: Int,
    val poster_path: String,
    val overview: String,
    val release_date: String,
    val title: String,
    val backdrop_path: String,
    val popularity: Number,
    val vote_average: Number,
    val genre_ids: ArrayList<Int>,
    val genre_names: ArrayList<String>,
    val genres: ArrayList<Genre>,
    val video : Boolean,
    val videos: Video
) {
    companion object {
        const val TABLE_NAME = "BOOKMARK_TABLE"
    }
}