package project.dheeraj.cinewatch.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.databinding.CastCardBinding
import project.dheeraj.cinewatch.ui.details.ActorDetailsActivity
import project.dheeraj.cinewatch.ui.details.MovieDetailsActivity
import project.dheeraj.cinewatch.utils.CONSTANTS

/**
 * Created by Dheeraj Kotwani on 26-03-2021.
 */
@ExperimentalCoroutinesApi
class CastRecyclerViewAdapter(
    val context : Context,
    val castList : ArrayList<Cast>
) : RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = CastCardBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_card, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.castImage.load(CONSTANTS.ImageBaseURL + castList[position].profile_path) {
                placeholder(CONSTANTS.actorPlaceHolder[position%4])
                error(CONSTANTS.actorPlaceHolder[position%4])
            }

            binding.castName.text = castList[position].name

            binding.root.setOnClickListener {
                onItemClicked(castList[position])
            }
        }
    }

    override fun getItemCount(): Int = Math.min(20, castList.size)

    private fun onItemClicked(cast : Cast, imageView : ImageView? = null) {

        ActorDetailsActivity.start(context, cast)

    }

}