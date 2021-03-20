package project.dheeraj.cinewatch.data.api

import project.dheeraj.cinewatch.data.model.Actor
import project.dheeraj.cinewatch.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Dheeraj Kotwani on 20-03-2021.
 */
interface NetworkService {

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movie_id: String
    ): Response<Movie>

    @GET("/movie/{movie_id}/images")
    suspend fun getMovieImage(
        @Path("movie_id") movie_id: String
    ): Response<Movie>

    @GET("/person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") person_id: String
    ): Response<Actor>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<List<Movie>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<List<Movie>>

    @GET("/movie/upcoming")
    suspend fun getUpcomingMovies(): Response<List<Movie>>

    @GET("/movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<List<Movie>>



    companion object {
        const val API_URL = "https://api.themoviedb.org/3/";
    }

}