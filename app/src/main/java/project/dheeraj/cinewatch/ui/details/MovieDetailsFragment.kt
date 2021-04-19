package project.dheeraj.cinewatch.ui.details

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import coil.load
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.local.dao.BookmarkDao
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.FragmentMovieDetailsBinding
import project.dheeraj.cinewatch.databinding.FragmentMovieDetailsBindingImpl
import project.dheeraj.cinewatch.ui.dialog.VideoPlayerDialog
import project.dheeraj.cinewatch.ui.adapter.CastRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.adapter.SimilarMoviesRecyclerViewAdapter
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast
import project.dheeraj.cinewatch.utils.toHours
import timber.log.Timber.e
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var movie : Movie

    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding : FragmentMovieDetailsBinding

    private var castList : ArrayList<Cast> = ArrayList()
    private var similarList : ArrayList<Movie> = ArrayList()

    private lateinit var castRecyclerViewAdapter : CastRecyclerViewAdapter
    private lateinit var similarRecyclerViewAdapter : SimilarMoviesRecyclerViewAdapter

    private lateinit var castSkeleton: Skeleton
    private lateinit var similarMovieSkeleton: Skeleton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        binding = FragmentMovieDetailsBinding.bind(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movie = requireArguments().get(CONSTANTS.movie) as Movie

        viewModel.movieName.value = movie.title
        viewModel.movie.value = movie
        viewModel.getVideos(movie.id)

        binding.buttonBack.setOnClickListener(this)
        binding.fabPlayButton.setOnClickListener(this)
        binding.buttonBookmark.setOnClickListener(this)

        initAdapters()

        loadData()

        loadCast()

        loadSimilar()

        checkBookmark()

        viewModel.getMovieDetails(movie.id)

    }

    private fun initAdapters() {
        similarRecyclerViewAdapter = SimilarMoviesRecyclerViewAdapter(requireContext(), similarList)
        castRecyclerViewAdapter = CastRecyclerViewAdapter(requireContext(), castList)

        binding.recyclerViewCast.adapter = castRecyclerViewAdapter
        binding.recyclerViewRelated.adapter = similarRecyclerViewAdapter

        castSkeleton = binding.recyclerViewCast.applySkeleton(R.layout.item_cast, 10)
        similarMovieSkeleton = binding.recyclerViewRelated.applySkeleton(R.layout.item_similar_movie, 10)
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {

        viewModel.movie.observe(requireActivity(), Observer {

            var genre: String = ""
            if (!it.genres.isNullOrEmpty())
                for (i in 0..it.genres.size - 1) {
                    genre += CONSTANTS.getGenreMap()[it.genres[i].id].toString()
                    if (i != it.genres.size - 1) {
                        genre += "• "
                    }
                }

            binding.apply {
                textMovieName.text = it!!.title
                textRating.text = "${it.vote_average}/10"
                textReleaseDate.text = it.release_date
                textDescription.text = it.overview
                if (it.runtime != null)
                    textLength.text = toHours(it.runtime!!)
                textGenres.text = genre

                detailsBannerImage.load(CONSTANTS.ImageBaseURL + it.backdrop_path) {
                    placeholder(CONSTANTS.viewPagerPlaceHolder.random())
                    error(CONSTANTS.viewPagerPlaceHolder.random())
                }

                imagePoster.load(CONSTANTS.ImageBaseURL + it.poster_path) {
                    placeholder(CONSTANTS.moviePlaceHolder.random())
                    error(CONSTANTS.moviePlaceHolder.random())
                }
            }

        })

    }

    private fun loadSimilar() {
        viewModel.loadSimilar(movie.id).observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    if (similarList.isNotEmpty())
                        similarMovieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    similarList.clear()
                    similarList.addAll(it.data!!.results)
                    similarRecyclerViewAdapter.notifyDataSetChanged()
                    similarMovieSkeleton.showOriginal()

                    if (similarList.isNullOrEmpty()) {
                        binding.headingRelated.visibility = View.GONE
                    } else {
                        binding.headingRelated.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }

    private fun loadCast() {
        viewModel.loadCast(movie.id).observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    if (castList.isNullOrEmpty())
                        castSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    castSkeleton.showOriginal()
                    castList.clear()
                    castList.addAll(it.data!!.cast)
                    castRecyclerViewAdapter.notifyDataSetChanged()

                    if (castList.isNullOrEmpty()) {
                        binding.headingCast.visibility = View.GONE
                    } else {
                        binding.headingCast.visibility = View.VISIBLE
                    }

                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }

    fun checkBookmark() {

        viewModel.bookmark.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if (it) {
                    buttonBookmark.setImageResource(R.drawable.ic_bookmark_done)
                }
                else {
                    buttonBookmark.setImageResource(R.drawable.ic_bookmark)
                }
            }
        })

        viewModel.checkBookmarkExist()

    }

    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.fabPlayButton -> {
                if (viewModel.videos.value != null && viewModel.videos.value!!.results.size != 0) {
                    val videoDialog = VideoPlayerDialog(viewModel.videos.value!!.results[0].key)
                    videoDialog.show(childFragmentManager, "Video Dialog")
                }
                else {
                    showToast("Video not found!")
                }
            }
            R.id.button_back -> {
                binding.root.findNavController().navigateUp()
            }
            R.id.button_bookmark -> {
                viewModel.bookmarkMovie()
                viewModel.checkBookmarkExist()
            }
        }

    }

}