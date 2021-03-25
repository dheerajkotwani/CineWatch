package project.dheeraj.cinewatch.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import coil.load
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Resource
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.ActivityMovieDetailsBinding
import project.dheeraj.cinewatch.ui.main.adapter.CastRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.main.adapter.SimilarMoviesRecyclerViewAdapter
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast

@ExperimentalCoroutinesApi
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel : MovieDetailsViewModel
    private lateinit var binding : ActivityMovieDetailsBinding

    private var castList : ArrayList<Cast> = ArrayList()
    private var similarList : ArrayList<Movie> = ArrayList()

    private lateinit var castRecyclerViewAdapter : CastRecyclerViewAdapter
    private lateinit var similarRecyclerViewAdapter : SimilarMoviesRecyclerViewAdapter

    companion object {

        private var movieId = 0;
        private var movie: Movie? = null

        fun getStartIntent(context : Context, movie : Movie) {
            context.startActivity(Intent(context, MovieDetailsActivity::class.java))
            this.movie = movie
            this.movieId = movie.id
        }

    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.viewmodel.se
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        viewModel.movieName.value = movie!!.title
        viewModel.movie.value = movie!!

        similarRecyclerViewAdapter = SimilarMoviesRecyclerViewAdapter(this, similarList)
        castRecyclerViewAdapter = CastRecyclerViewAdapter(this, castList)

        binding.recyclerViewCast.adapter = castRecyclerViewAdapter
        binding.recyclerViewRelated.adapter = similarRecyclerViewAdapter

        viewModel.movie.observe(this, Observer {
            showToast("Update")
            binding.textMovieName.text = it!!.title
            binding.textRating.text = "${ it.vote_average }/10"
            binding.textReleaseDate.text = it.release_date
            binding.textDescription.text = it.overview

            var genre : String = ""
            for (i in 0..it.genre_ids.size-1) {
                genre += CONSTANTS.getGenreMap()[it.genre_ids[i]].toString()
                if (i != it.genre_ids.size - 1) {
                    genre += "• "
                }
            }
            binding.textGenres.text = genre

            binding.detailsBannerImage.load(CONSTANTS.ImageBaseURL + it.backdrop_path)
            binding.imagePoster.load(CONSTANTS.ImageBaseURL + it.poster_path)
        })

        viewModel.loadCast(movieId).observe(this, Observer{
            when(it.status) {
                Status.LOADING -> {
                    // TODO Shimmer Layout
                    showToast("Loading")
                }
                Status.SUCCESS -> {
                    Log.e("Cast", it.data!!.cast.toString())
                    castList.addAll(it.data!!.cast)
                    castRecyclerViewAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })

        viewModel.loadSimilar(movieId).observe(this, Observer{
            when(it.status) {
                Status.LOADING -> {
                    // TODO Shimmer Layout
                    Log.e("Load Similar", "loading")
                    showToast("Loading")
                }
                Status.SUCCESS -> {
                    similarList.addAll(it.data!!.results)
                    Log.e("Similar" , it.data.results.toString())
                    similarRecyclerViewAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })

        
    }
}