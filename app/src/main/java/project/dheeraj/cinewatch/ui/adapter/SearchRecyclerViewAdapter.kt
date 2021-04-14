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
import project.dheeraj.cinewatch.databinding.ItemMovieHomeBinding
import project.dheeraj.cinewatch.databinding.ItemSearchBinding
import project.dheeraj.cinewatch.utils.CONSTANTS

/**
 * Created by Dheeraj Kotwani on 22-03-2021.
 */

@ExperimentalCoroutinesApi
class SearchRecyclerViewAdapter(
        val context : Context,
        val movies : ArrayList<Movie>
) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSearchBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {

            binding.searchImage.load(CONSTANTS.ImageBaseURL + movies[position].poster_path) {
                placeholder(CONSTANTS.moviePlaceHolder[position%4])
                error(CONSTANTS.moviePlaceHolder[position%4])
            }

            itemView.setOnClickListener {
                val bundle = bundleOf(CONSTANTS.movie to movies[position])
                it.findNavController().navigate(R.id.action_searchFragment_to_movieDetailsFragment, bundle)
            }
        }

    }

    override fun getItemCount() = movies.size


}