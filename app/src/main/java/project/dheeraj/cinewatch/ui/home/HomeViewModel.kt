package project.dheeraj.cinewatch.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.data.model.Resource
import project.dheeraj.cinewatch.data.repository.NetworkRepository
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(
    private val repository : NetworkRepository
) : ViewModel() {

    fun loadNowPlaying() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = repository.getNowPlayingMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadUpcoming() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = repository.getUpcomingMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadPopular() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = repository.getPopularMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadTopRated() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = repository.getTopRatedMovie()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }


}