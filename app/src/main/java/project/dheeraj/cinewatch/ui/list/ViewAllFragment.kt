package project.dheeraj.cinewatch.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.FragmentViewAllBinding
import project.dheeraj.cinewatch.ui.adapter.SearchRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.adapter.ViewAllRecyclerViewAdapter
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast

@ExperimentalCoroutinesApi
class ViewAllFragment : Fragment() {

    companion object {
        fun newInstance() = ViewAllFragment()
    }

    private lateinit var binding: FragmentViewAllBinding
    private lateinit var viewModel: ViewAllViewModel
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
        viewModel = ViewModelProvider(this).get(ViewAllViewModel::class.java)

        movieAdapter = ViewAllRecyclerViewAdapter(requireContext(), moviesList)
        binding.movieRecyclerView.adapter = movieAdapter

        movieSkeleton = binding.movieRecyclerView.applySkeleton(R.layout.item_search, 15)

        val pageType = requireArguments().get(CONSTANTS.viewAll)
        binding.pageTitle.text = pageType.toString()
        when(pageType) {
            CONSTANTS.Upcoming -> fetchUpcoming()
            CONSTANTS.TopRated -> fetchTopRated()
            CONSTANTS.Popular -> fetchPopular()
        }

    }

    fun fetchPopular() {
        viewModel.fetchPopular().observe(requireActivity(), Observer { res ->

            when(res.status) {
                Status.LOADING -> {
                    movieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    movieSkeleton.showOriginal()
                    moviesList.clear()
                    moviesList.addAll(res.data!!.results)
                    movieAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }

        })
    }

    fun fetchTopRated() {

        viewModel.fetchTopRated().observe(requireActivity(), Observer { res ->

            when(res.status) {
                Status.LOADING -> {
                    movieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    movieSkeleton.showOriginal()
                    moviesList.clear()
                    moviesList.addAll(res.data!!.results)
                    movieAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }

        })

    }

    fun fetchUpcoming() {

        viewModel.fetchUpcoming().observe(requireActivity(), Observer { res ->

            when(res.status) {
                Status.LOADING -> {
                    movieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    movieSkeleton.showOriginal()
                    moviesList.clear()
                    moviesList.addAll(res.data!!.results)
                    movieAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })

    }

}