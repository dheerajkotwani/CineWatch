package project.dheeraj.cinewatch.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.databinding.ItemSimilarMovieBinding
import project.dheeraj.cinewatch.utils.CONSTANTS

/**
 * Created by Dheeraj Kotwani on 26-03-2021.
 */

@ExperimentalCoroutinesApi
class SimilarMoviesRecyclerViewAdapter(
    val context : Context,
    val movies : ArrayList<Movie>
) : RecyclerView.Adapter<SimilarMoviesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSimilarMovieBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_similar_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            if (position == 0) {
                binding.spacingStart.visibility = View.VISIBLE
            }
            else if (position == movies.size-1) {
                binding.spacingEnd.visibility = View.VISIBLE
            }
            else {
                binding.spacingEnd.visibility = View.GONE
                binding.spacingStart.visibility = View.GONE
            }

            binding.movieImage.load(CONSTANTS.ImageBaseURL + movies[position].poster_path) {
                placeholder(CONSTANTS.moviePlaceHolder[position%4])
                error(CONSTANTS.moviePlaceHolder[position%4])
            }

            binding.movieImage.setOnClickListener {
                val bundle = bundleOf(CONSTANTS.movie to movies[position])
                it.findNavController().navigate(R.id.action_movieDetailsFragment_self, bundle)
            }
        }
    }

    override fun getItemCount(): Int = movies.size

}