package project.dheeraj.cinewatch.data.model

import org.json.JSONObject

/**
 * Created by Dheeraj Kotwani on 19-03-2021.
 */
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
)