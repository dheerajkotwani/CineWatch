package project.dheeraj.cinewatch.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.FragmentViewAllBinding
import project.dheeraj.cinewatch.ui.adapter.BookmarkRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.adapter.SearchRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.adapter.ViewAllRecyclerViewAdapter
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast
import timber.log.Timber.e

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ViewAllFragment : Fragment() {

    private lateinit var binding: FragmentViewAllBinding
    private val viewModel: ViewAllViewModel by viewModels()

    private lateinit var movieAdapter: ViewAllRecyclerViewAdapter
    private lateinit var movieSkeleton: Skeleton

    private var moviesList: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_all, container, false)
        binding = FragmentViewAllBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieAdapter = ViewAllRecyclerViewAdapter()
        binding.movieRecyclerView.adapter = movieAdapter

        movieSkeleton = binding.movieRecyclerView.applySkeleton(R.layout.item_search, 15)

        val pageType = requireArguments().get(CONSTANTS.viewAll)
        binding.pageTitle.text = pageType.toString()
        when(pageType) {
            CONSTANTS.Upcoming -> fetchUpcoming()
            CONSTANTS.TopRated -> fetchTopRated()
            CONSTANTS.Popular -> fetchPopular()
            CONSTANTS.Bookmarks -> fetchBookmarks()
        }

        binding.buttonBack.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

    }

    fun fetchBookmarks() {

        viewModel.bookmarks.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.movieRecyclerView.adapter = BookmarkRecyclerViewAdapter(it)
            }
        }

        viewModel.fetchBookmarks()

    }

    fun fetchPopular() {
        viewModel.fetchPopular().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
    }

    fun fetchTopRated() {

        viewModel.fetchTopRated().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

    fun fetchUpcoming() {

        viewModel.fetchUpcoming().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

}