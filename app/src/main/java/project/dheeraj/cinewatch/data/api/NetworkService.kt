package project.dheeraj.cinewatch.data.api

import project.dheeraj.cinewatch.data.model.Actor
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.MovieResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Dheeraj Kotwani on 20-03-2021.
 */
interface NetworkService {

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Query ("api_key") apiKey : String,
        @Path("movie_id") movie_id: String
    ): Response<Movie>

    @GET("/movie/{movie_id}/images")
    suspend fun getMovieImage(
        @Query ("api_key") apiKey : String,
        @Path("movie_id") movie_id: String
    ): Response<Movie>

    @GET("/person/{person_id}")
    suspend fun getPerson(
        @Query ("api_key") apiKey : String,
        @Path("person_id") person_id: String
    ): Response<Actor>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query ("api_key") apiKey : String
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query ("api_key") apiKey : String
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query ("api_key") apiKey : String
    ): Response<MovieResponse>

    @GET("/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query ("api_key") apiKey : String
    ): Response<List<Movie>>



    companion object {
        const val API_URL = "https://api.themoviedb.org/3/";

        operator fun invoke() : NetworkService {

            return Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NetworkService::class.java)

        }

    }

}