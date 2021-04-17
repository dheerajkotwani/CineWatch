package project.dheeraj.cinewatch.data.api

import project.dheeraj.cinewatch.data.model.*
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
        @Path("movie_id") movie_id: Int,
        @Query ("api_key") apiKey : String
    ): Response<Movie>

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImage(
        @Path("movie_id") movie_id: Int,
        @Query ("api_key") apiKey : String
    ): Response<Movie>


    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
            @Path("movie_id") movie_id: Int,
            @Query ("api_key") apiKey : String
    ): Response<VideoResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movie_id: Int,
        @Query ("api_key") apiKey : String
    ): Response<CastCreditsResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query ("api_key") apiKey : String
    ): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query ("api_key") apiKey : String,
        @Query("page") page : Int = 1
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query ("api_key") apiKey : String,
        @Query("page") page : Int = 1
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query ("api_key") apiKey : String,
        @Query("page") page : Int = 1
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query ("api_key") apiKey : String
    ): Response<MovieResponse>


    // Person
    @GET("person/{person_id}")
    suspend fun getPerson(
            @Path("person_id") person_id: Int,
            @Query ("api_key") apiKey : String
    ): Response<Actor>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCredits(
            @Path("person_id") person_id: Int,
            @Query ("api_key") apiKey : String
    ): Response<MovieCreditsResponse>

    // Search
    @GET("search/movie")
    suspend fun searchMovie(
            @Query("query") movie_id: String,
            @Query("page") page: Int,
            @Query ("api_key") apiKey : String
    ): Response<MovieResponse>


}