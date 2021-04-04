package project.dheeraj.cinewatch.data.model

/**
 * Created by Dheeraj Kotwani on 27-03-2021.
 */
data class Video (
    val id : String,
    val iso_639_1 : String,
    val iso_3166_1 : String,
    val key : String,
    val name : String,
    val site : String,
    val size : Int,
    val type : String
)