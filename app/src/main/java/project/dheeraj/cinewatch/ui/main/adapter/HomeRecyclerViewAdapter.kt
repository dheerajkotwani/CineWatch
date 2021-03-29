package project.dheeraj.cinewatch.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.databinding.HomeMovieCardBinding
import project.dheeraj.cinewatch.ui.details.MovieDetailsActivity
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.getMoviePlaceHolder

/**
 * Created by Dheeraj Kotwani on 22-03-2021.
 */
@ExperimentalCoroutinesApi
class HomeRecyclerViewAdapter(
        val context : Context,
        val movies : ArrayList<Movie>
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = HomeMovieCardBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_movie_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            binding.movieImage.load(CONSTANTS.ImageBaseURL + movies[position].poster_path) {
                placeholder(CONSTANTS.moviePlaceHolder[position%4])
                error(CONSTANTS.moviePlaceHolder[position%4])
            }
            binding.textMovieName.text = movies[position].title
            binding.textMovieRating.text = movies[position].vote_average.toString()

            itemView.setOnClickListener {
                onItemClicked(movies[position])
            }
        }

    }

    override fun getItemCount() = movies.size

    private fun onItemClicked(movie : Movie, imageView : ImageView? = null) {

        MovieDetailsActivity.getStartIntent(context, movie)

    }

}