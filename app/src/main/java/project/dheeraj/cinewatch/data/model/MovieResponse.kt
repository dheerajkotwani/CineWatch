package project.dheeraj.cinewatch.data.model

/**
 * Created by Dheeraj Kotwani on 22-03-2021.
 */
data class MovieResponse  (
    val page : Int,
    val results : List<Movie>,
    val total_results: Int,
    val total_pages: Int
)