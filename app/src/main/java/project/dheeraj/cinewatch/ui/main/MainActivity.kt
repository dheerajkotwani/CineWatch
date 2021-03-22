package project.dheeraj.cinewatch.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.State
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.ActivityMainBinding
import project.dheeraj.cinewatch.ui.main.adapter.HomeRecyclerViewAdapter
import project.dheeraj.cinewatch.ui.main.viewholder.MainViewModel
import project.dheeraj.cinewatch.utils.showToast

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var movieList : ArrayList<Movie> = ArrayList()
    private lateinit var popularAdapter : HomeRecyclerViewAdapter

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

        popularAdapter = HomeRecyclerViewAdapter(this, movieList)
        binding.recyclerViewPopular.adapter = popularAdapter

        viewModel.loadPopular().observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    showToast("Loading")
                }
                Status.SUCCESS -> {
                    movieList.addAll(res.data!!.results)
                    popularAdapter.notifyDataSetChanged()
                    showToast(res.status.toString())
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

    }
}