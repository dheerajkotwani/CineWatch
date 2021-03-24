package project.dheeraj.cinewatch.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.ActivityMainBinding
import project.dheeraj.cinewatch.ui.details.MovieDetailsActivity
import project.dheeraj.cinewatch.ui.main.adapter.HomeRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.main.viewholder.MainViewModel
import project.dheeraj.cinewatch.utils.showToast

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var binding: ActivityMainBinding

    private var upcomingMovieList : ArrayList<Movie> = ArrayList()
    private var popularMovieList : ArrayList<Movie> = ArrayList()
    private var topRatedMovieList : ArrayList<Movie> = ArrayList()

    private lateinit var upcomingAdapter : HomeRecyclerViewAdapter
    private lateinit var popularAdapter : HomeRecyclerViewAdapter
    private lateinit var topRatedAdapter : HomeRecyclerViewAdapter

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initAdapters()

        viewModel.loadUpcoming().observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    showToast("Loading")
                }
                Status.SUCCESS -> {
                    upcomingMovieList.addAll(res.data!!.results)
                    upcomingAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadPopular().observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
//                    showToast("Loading")
                }
                Status.SUCCESS -> {
                    Log.e("Result", res.data!!.results[0].backdrop_path.toString())
                    popularMovieList.addAll(res.data!!.results)
                    popularAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadTopRated().observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
//                    showToast("Loading")
                }
                Status.SUCCESS -> {
                    topRatedMovieList.addAll(res.data!!.results)
                    topRatedAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })
    }

    private fun onItemClicked(movie : Movie, imageView : ImageView?) {

//        val intent = MovieDetailsActivity.getStartIntent(this, movie)
//        startActivity(intent)

    }


    private fun initAdapters() {
        upcomingAdapter = HomeRecyclerViewAdapter(this, upcomingMovieList)
        binding.recyclerViewUpcoming.adapter = upcomingAdapter

        popularAdapter = HomeRecyclerViewAdapter(this, popularMovieList)
        binding.recyclerViewPopular.adapter = popularAdapter

        topRatedAdapter = HomeRecyclerViewAdapter(this, topRatedMovieList)
        binding.recyclerViewTopRated.adapter = topRatedAdapter
    }

}