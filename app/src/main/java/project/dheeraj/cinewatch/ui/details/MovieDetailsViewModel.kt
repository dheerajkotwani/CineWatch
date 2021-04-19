package project.dheeraj.cinewatch.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import project.dheeraj.cinewatch.data.local.dao.BookmarkDao
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.MovieDB
import project.dheeraj.cinewatch.data.model.Resource
import project.dheeraj.cinewatch.data.model.VideoResponse
import project.dheeraj.cinewatch.data.repository.NetworkRepository
import timber.log.Timber.e
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class MovieDetailsViewModel @ViewModelInject constructor(
        private val databaseDao : BookmarkDao,
        private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _name = MutableLiveData("Movie Name")
    private val _movie = MutableLiveData<Movie>()
    private val _videos = MutableLiveData<VideoResponse>()

    var bookmark = MutableLiveData(false)
    var movieName : MutableLiveData<String> = _name
    var movie : MutableLiveData<Movie> = _movie
    var videos : MutableLiveData<VideoResponse> = _videos

    fun getMovieDetails(movie_id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = networkRepository.getMovieDetails(movie_id)
            movie.postValue(apiResponse)
        }
    }

    fun loadCast(movie_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = networkRepository.getMovieCredits(movie_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadSimilar(movie_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = networkRepository.getSimilarMovies(movie_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getVideos(movie_id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResponse = networkRepository.getVideos(movie_id)
                videos.postValue(apiResponse)
            } catch (e: Exception) {
                e(e)
            }
        }

    }

    fun bookmarkMovie() {
        movie.value!!.apply {
            val movieDb = MovieDB(id, poster_path!!, overview!!, title!!, backdrop_path!!)
            viewModelScope.launch(Dispatchers.IO) {
                if (bookmark.value == true) {
                    databaseDao.removeMovie(movieDb)
                }
                else {
                    databaseDao.insertMovie(movieDb)
                }
            }
            e("Bookmark")
        }
    }

    fun checkBookmarkExist() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = databaseDao.bookmarkExist(movie.value!!.id)
            bookmark.postValue(response)
        }
    }

}