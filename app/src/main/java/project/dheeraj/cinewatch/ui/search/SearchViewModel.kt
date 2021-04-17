package project.dheeraj.cinewatch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import project.dheeraj.cinewatch.data.model.Resource
import project.dheeraj.cinewatch.data.repository.NetworkRepository
import java.net.SocketTimeoutException

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val networkRepository = NetworkRepository()

    fun searchMovie(query: String, page: Int = 1) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse =  networkRepository.searchMovie(query, page)
            emit(Resource.success(apiResponse))
        }
        catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getSearchMovie(query: String) =
        networkRepository.getSearchResult(query).cachedIn(viewModelScope)



}