package project.dheeraj.cinewatch.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.ActivityMovieDetailsBinding
import project.dheeraj.cinewatch.ui.dialog.VideoDialog
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

    private var videoId = null

    private lateinit var castRecyclerViewAdapter : CastRecyclerViewAdapter
    private lateinit var similarRecyclerViewAdapter : SimilarMoviesRecyclerViewAdapter

    private lateinit var castSkeleton: Skeleton
    private lateinit var similarMovieSkeleton: Skeleton

    companion object {

        private var movieId = 0;
        private var movie: Movie? = null

        fun getStartIntent(context: Context, movie: Movie) {
            context.startActivity(Intent(context, MovieDetailsActivity::class.java))
            this.movie = movie
            this.movieId = movie.id
        }

    }
    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setLifecycleOwner(this)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        castSkeleton = binding.recyclerViewCast.applySkeleton(R.layout.cast_card, 10)
        similarMovieSkeleton = binding.recyclerViewRelated.applySkeleton(R.layout.similar_movie_card, 10)

        viewModel.movieName.value = movie!!.title
        viewModel.movie.value = movie!!

        binding.fabPlayButton.setOnClickListener {

            if (viewModel.videos.value != null && viewModel.videos.value!!.results.size != 0) {
                val videoDialog = VideoDialog(viewModel.videos.value!!.results[0].key)
                videoDialog.show(supportFragmentManager, "Video Dialog")
            }
            else {
                showToast("Video not found!")
            }

        }

        similarRecyclerViewAdapter = SimilarMoviesRecyclerViewAdapter(this, similarList)
        castRecyclerViewAdapter = CastRecyclerViewAdapter(this, castList)

        binding.recyclerViewCast.adapter = castRecyclerViewAdapter
        binding.recyclerViewRelated.adapter = similarRecyclerViewAdapter

        viewModel.getVideos(movieId).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    // TODO Shimmer Layout
                }
                Status.SUCCESS -> {
                    Log.e("Video", it.data!!.results.toString())
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })

        viewModel.movie.observe(this, Observer {
            binding.textMovieName.text = it!!.title
            binding.textRating.text = "${it.vote_average}/10"
            binding.textReleaseDate.text = it.release_date
            binding.textDescription.text = it.overview


            var genre: String = ""
            for (i in 0..it.genre_ids.size - 1) {
                genre += CONSTANTS.getGenreMap()[it.genre_ids[i]].toString()
                if (i != it.genre_ids.size - 1) {
                    genre += "• "
                }
            }
            binding.textGenres.text = genre

            binding.detailsBannerImage.load(CONSTANTS.ImageBaseURL + it.backdrop_path)
            binding.imagePoster.load(CONSTANTS.ImageBaseURL + it.poster_path)
        })



        viewModel.loadCast(movieId).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
//                    castSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
//                    castSkeleton.showOriginal()
                    castList.clear()
                    castList.addAll(it.data!!.cast)
                    castRecyclerViewAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })

        viewModel.loadSimilar(movieId).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    similarMovieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    similarMovieSkeleton.showOriginal()
                    similarList.clear()
                    similarList.addAll(it.data!!.results)
                    similarRecyclerViewAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })

        
    }
}