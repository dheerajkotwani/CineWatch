package project.dheeraj.cinewatch.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.data.model.Resource
import project.dheeraj.cinewatch.data.repository.NetworkRepository
import java.lang.Exception
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class ViewAllViewModel : ViewModel() {

    private val repository = NetworkRepository()

    fun fetchPopular() =
        repository.getPopularMovieResult().cachedIn(viewModelScope)

    fun fetchUpcoming() =
        repository.getUpcomingMovieResult().cachedIn(viewModelScope)

    fun fetchTopRated() =
        repository.getTopRatedMovieResult().cachedIn(viewModelScope)


}