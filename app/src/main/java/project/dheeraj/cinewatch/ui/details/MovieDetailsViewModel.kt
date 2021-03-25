package project.dheeraj.cinewatch.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.dheeraj.cinewatch.data.model.Movie

class MovieDetailsViewModel : ViewModel() {

    private val _name = MutableLiveData("Movie Name")
    private val _movie = MutableLiveData<Movie>()

    var movieName : MutableLiveData<String> = _name
    var movie : MutableLiveData<Movie> = _movie
//    var movie : String = "Hello"


}