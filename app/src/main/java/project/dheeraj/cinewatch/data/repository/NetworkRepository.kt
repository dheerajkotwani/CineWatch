package project.dheeraj.cinewatch.data.repository

import project.dheeraj.cinewatch.data.api.NetworkService
import project.dheeraj.cinewatch.data.api.SafeApiRequest
import project.dheeraj.cinewatch.utils.CONSTANTS

/**
 * Created by Dheeraj Kotwani on 21-03-2021.
 */
class NetworkRepository : SafeApiRequest() {

    private val networkApi = NetworkService()

    // Now Playing Movies
    suspend fun getNowPlayingMovie() = apiRequest {
        networkApi.getPopularMovies(CONSTANTS.API_KEY)
    }

    // Upcoming Movies
    suspend fun getUpcomingMovie() = apiRequest {
        networkApi.getUpcomingMovies(CONSTANTS.API_KEY)
    }

    // Popular Movies
    suspend fun getPopularMovie() = apiRequest {
        networkApi.getPopularMovies(CONSTANTS.API_KEY)
    }

    // Top Rated Movies
    suspend fun getTopRatedMovie() = apiRequest {
        networkApi.getTopRatedMovies(CONSTANTS.API_KEY)
    }

    // Movie Credits
    suspend fun getMovieCredits(movie_id : Int) = apiRequest {
        networkApi.getMovieCredits(movie_id, CONSTANTS.API_KEY)
    }

    // Get Similar Movies
    suspend fun getSimilarMovies(movie_id : Int) = apiRequest {
        networkApi.getSimilarMovies(movie_id, CONSTANTS.API_KEY)
    }

}