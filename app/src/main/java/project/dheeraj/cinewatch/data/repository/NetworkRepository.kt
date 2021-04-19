package project.dheeraj.cinewatch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.liveData
import project.dheeraj.cinewatch.data.api.NetworkService
import project.dheeraj.cinewatch.data.api.SafeApiRequest
import project.dheeraj.cinewatch.di.module.ApiModule
import project.dheeraj.cinewatch.ui.paging.*
import project.dheeraj.cinewatch.utils.CONSTANTS
import javax.inject.Inject

/**
 * Created by Dheeraj Kotwani on 21-03-2021.
 */
class NetworkRepository @Inject constructor(
    private val networkApi: NetworkService
) : SafeApiRequest() {

    // Get Movie Details
    suspend fun getMovieDetails(movie_id : Int) = apiRequest {
        networkApi.getMovieById(movie_id, CONSTANTS.API_KEY)
    }

    // Now Playing Movies
    suspend fun getNowPlayingMovie() = apiRequest {
        networkApi.getNowPlayingMovies(CONSTANTS.API_KEY)
    }

    // Get Videos
    suspend fun getVideos(movie_id : Int) = apiRequest {
        networkApi.getVideos(movie_id, CONSTANTS.API_KEY)
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

    // Get Person
    suspend fun getPerson(personId : Int) = apiRequest {
        networkApi.getPerson(personId, CONSTANTS.API_KEY)
    }

    // Person Movie Credits
    suspend fun getPersonMovieCredits(personId : Int) = apiRequest {
        networkApi.getPersonMovieCredits(personId, CONSTANTS.API_KEY)
    }

    // Search Movie
    suspend fun searchMovie(query: String, page: Int) = apiRequest {
        networkApi.searchMovie(query, page, CONSTANTS.API_KEY)
    }

    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                SearchPagingSource(networkApi, query)
            }
        ).liveData

    fun getPopularMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                PopularPagingSource(networkApi)
            }
        ).liveData

    fun getUpcomingMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                UpcomingPagingSource(networkApi)
            }
        ).liveData

    fun getTopRatedMovieResult() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 500
            ),
            pagingSourceFactory = {
                TopRatedPagingSource(networkApi)
            }
        ).liveData

}