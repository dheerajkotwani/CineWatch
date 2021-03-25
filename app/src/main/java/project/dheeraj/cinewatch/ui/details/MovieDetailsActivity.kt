package project.dheeraj.cinewatch.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import coil.load
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.databinding.ActivityMovieDetailsBinding
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel : MovieDetailsViewModel
    private lateinit var binding : ActivityMovieDetailsBinding

    companion object {

        private const val MOVIE_ID = "movie_id";
        private var movie: Movie? = null

        fun getStartIntent(context : Context, movie : Movie) {
            context.startActivity(Intent(context, MovieDetailsActivity::class.java))
            this.movie = movie
        }

    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        viewModel.movieName.value = movie!!.title
        viewModel.movie.value = movie!!

        viewModel.movie.observe(this, Observer {
            showToast("Update")
            binding.textMovieName.text = it!!.title
            binding.textRating.text = "${ it.vote_average }/10"
            binding.textReleaseDate.text = it.release_date
            binding.textDescription.text = it.overview

            binding.detailsBannerImage.load(CONSTANTS.ImageBaseURL + it.backdrop_path)
            binding.imagePoster.load(CONSTANTS.ImageBaseURL + it.poster_path)
        })

        showToast(movie!!.title)

        
    }
}