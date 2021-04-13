package project.dheeraj.cinewatch.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.data.model.Resource
import project.dheeraj.cinewatch.data.repository.NetworkRepository
import java.lang.Exception
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class ViewAllViewModel : ViewModel() {

    private val repository = NetworkRepository()

    fun fetchPopular() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getPopularMovie()
            emit(Resource.success(apiResponse))
        }
        catch(e : Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun fetchUpcoming() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getUpcomingMovie()
            emit(Resource.success(apiResponse))
        }
        catch(e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun fetchTopRated() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse = repository.getTopRatedMovie()
            emit(Resource.success(apiResponse))
        }
        catch(e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }


}