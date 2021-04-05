package project.dheeraj.cinewatch.ui.main.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.databinding.FragmentHomeViewPagerBinding
import project.dheeraj.cinewatch.databinding.HomeViewPagerCardBinding
import project.dheeraj.cinewatch.ui.details.MovieDetailsActivity
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.getMoviePlaceHolder

@ExperimentalCoroutinesApi
class HomeViewPagerFragment(
    val movie: Movie
) : Fragment() {

    private lateinit var binding : FragmentHomeViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_view_pager, container, false)
        binding = FragmentHomeViewPagerBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewPagerImage.load(CONSTANTS.ImageBaseURL + movie.backdrop_path) {
            placeholder(CONSTANTS.viewPagerPlaceHolder.random())
            error(CONSTANTS.viewPagerPlaceHolder.random())
        }
        binding.viewPagerText.text = movie.title

        binding.root.setOnClickListener {
            MovieDetailsActivity.getStartIntent(requireContext(), movie)
        }

    }
}