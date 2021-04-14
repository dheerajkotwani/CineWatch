package project.dheeraj.cinewatch.ui.details

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import coil.load
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.MovieDetailsFragmentBinding
import project.dheeraj.cinewatch.ui.dialog.VideoPlayerDialog
import project.dheeraj.cinewatch.ui.adapter.CastRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.adapter.SimilarMoviesRecyclerViewAdapter
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast

@ExperimentalCoroutinesApi
class MovieDetailsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private lateinit var movie : Movie

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding : MovieDetailsFragmentBinding

    private var castList : ArrayList<Cast> = ArrayList()
    private var similarList : ArrayList<Movie> = ArrayList()

    private var videoId = null

    private lateinit var castRecyclerViewAdapter : CastRecyclerViewAdapter
    private lateinit var similarRecyclerViewAdapter : SimilarMoviesRecyclerViewAdapter

    private lateinit var castSkeleton: Skeleton
    private lateinit var similarMovieSkeleton: Skeleton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_details_fragment, container, false)
        binding = MovieDetailsFragmentBinding.bind(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        movie = requireArguments().get(CONSTANTS.movie) as Movie

        viewModel.movieName.value = movie.title
        viewModel.movie.value = movie
        viewModel.getVideos(movie.id)
        binding.fabPlayButton.setOnClickListener(this)

        initAdapters()

        loadData()

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

            binding.detailsBannerImage.load(CONSTANTS.ImageBaseURL + it.backdrop_path) {
                placeholder(CONSTANTS.viewPagerPlaceHolder.random())
                error(CONSTANTS.viewPagerPlaceHolder.random())
            }
            binding.imagePoster.load(CONSTANTS.ImageBaseURL + it.poster_path) {
                placeholder(CONSTANTS.moviePlaceHolder.random())
                error(CONSTANTS.moviePlaceHolder.random())
            }
        })


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
                    }
                    else {
                        binding.headingCast.visibility = View.VISIBLE
                    }

                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })

        viewModel.loadSimilar(movie.id).observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    if(similarList.isNotEmpty())
                        similarMovieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    similarList.clear()
                    similarList.addAll(it.data!!.results)
                    similarRecyclerViewAdapter.notifyDataSetChanged()
                    similarMovieSkeleton.showOriginal()

                    if (similarList.isNullOrEmpty()) {
                        binding.headingRelated.visibility = View.GONE
                    }
                    else {
                        binding.headingRelated.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
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
        }

    }

}