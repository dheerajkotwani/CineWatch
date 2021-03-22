package project.dheeraj.cinewatch.ui.main.viewholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.data.model.Resource
import project.dheeraj.cinewatch.data.model.State
import project.dheeraj.cinewatch.data.repository.NetworkRepository
import project.dheeraj.cinewatch.utils.CONSTANTS
import timber.log.Timber.e
import java.net.SocketTimeoutException

/**
 * Created by Dheeraj Kotwani on 21-03-2021.
 */

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val repository = NetworkRepository()

    fun loadUpcoming() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = repository.getUpcomingMovie()
//            e(apiResponse)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadPopular() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            e("try Request")
            // Fetch data from remote
            val apiResponse = repository.getPopularMovie()
            e(apiResponse.toString())

            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            e(e)

            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadTopRated() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = repository.getTopRatedMovie()

            // Parse Body
//            val remoteData = apiResponse.body()
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

}