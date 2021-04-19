package project.dheeraj.cinewatch.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.FragmentHomeBinding
import project.dheeraj.cinewatch.ui.adapter.HomeRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.adapter.HomeViewPagerAdapter
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast
import java.lang.Math.abs

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    private val viewModel : HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private var upcomingMovieList : ArrayList<Movie> = ArrayList()
    private var popularMovieList : ArrayList<Movie> = ArrayList()
    private var topRatedMovieList : ArrayList<Movie> = ArrayList()

    private lateinit var upcomingAdapter : HomeRecyclerViewAdapter
    private lateinit var popularAdapter : HomeRecyclerViewAdapter
    private lateinit var topRatedAdapter : HomeRecyclerViewAdapter

    private lateinit var viewPagerSkeleton : Skeleton
    private lateinit var upcomingSkeleton : Skeleton
    private lateinit var topRatedSkeleton : Skeleton
    private lateinit var popularSkeleton : Skeleton

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        binding.homeViewPager.setPageTransformer { page, position ->
            page.translationX = -10 * position
            page.scaleY = 1 - (0.25f * abs(position))
        }

        binding.homeSearchButton.setOnClickListener(this)
        binding.bookmarks.setOnClickListener(this)
        binding.textViewAllPopular.setOnClickListener(this)
        binding.textViewAllTopRated.setOnClickListener(this)
        binding.textViewAllUpcoming.setOnClickListener(this)

        initAdapters()

        initSkeletons()

        fetchData()

    }

    private fun fetchData() {
        viewModel.loadNowPlaying().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    viewPagerSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    viewPagerSkeleton.showOriginal()
                    binding.homeViewPager.adapter =
                        HomeViewPagerAdapter(childFragmentManager, lifecycle, res.data!!.results)
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadUpcoming().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (upcomingMovieList.isNullOrEmpty())
                        upcomingSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    upcomingSkeleton.showOriginal()
                    upcomingMovieList.clear()
                    upcomingMovieList.addAll(res.data!!.results)
                    upcomingAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadPopular().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (popularMovieList.isNullOrEmpty())
                        popularSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    popularSkeleton.showOriginal()
                    popularMovieList.clear()
                    popularMovieList.addAll(res.data!!.results)
                    popularAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadTopRated().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (topRatedMovieList.isNullOrEmpty())
                        topRatedSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    topRatedSkeleton.showOriginal()
                    topRatedMovieList.clear()
                    topRatedMovieList.addAll(res.data!!.results)
                    topRatedAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })
    }

    @SuppressLint("ResourceType")
    private fun initSkeletons()
    {
        viewPagerSkeleton = binding.homeViewPager.applySkeleton(R.layout.fragment_home_view_pager)

        upcomingSkeleton = binding.recyclerViewUpcoming.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )

        popularSkeleton = binding.recyclerViewPopular.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )

        topRatedSkeleton = binding.recyclerViewTopRated.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )
    }


    private fun initAdapters() {
        upcomingAdapter = HomeRecyclerViewAdapter(requireContext(), upcomingMovieList)
        binding.recyclerViewUpcoming.adapter = upcomingAdapter

        popularAdapter = HomeRecyclerViewAdapter(requireContext(), popularMovieList)
        binding.recyclerViewPopular.adapter = popularAdapter

        topRatedAdapter = HomeRecyclerViewAdapter(requireContext(), topRatedMovieList)
        binding.recyclerViewTopRated.adapter = topRatedAdapter
    }


    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.home_search_button -> {
                navController.navigate(R.id.action_homeFragment_to_searchFragment2)
            }
            R.id.text_view_all_popular -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.Popular)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.text_view_all_top_rated -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.TopRated)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.text_view_all_upcoming -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.Upcoming)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.bookmarks -> {
                val bundle = bundleOf(CONSTANTS.viewAll to CONSTANTS.Bookmarks)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }

        }

    }

}