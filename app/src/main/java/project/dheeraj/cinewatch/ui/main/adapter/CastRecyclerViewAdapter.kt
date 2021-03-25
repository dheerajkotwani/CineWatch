package project.dheeraj.cinewatch.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.databinding.CastCardBinding
import project.dheeraj.cinewatch.utils.CONSTANTS

/**
 * Created by Dheeraj Kotwani on 26-03-2021.
 */
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
            binding.castImage.load(CONSTANTS.ImageBaseURL + castList[position].profile_path)
            binding.castName.text = castList[position].name
        }
    }

    override fun getItemCount(): Int = Math.min(20, castList.size)

}