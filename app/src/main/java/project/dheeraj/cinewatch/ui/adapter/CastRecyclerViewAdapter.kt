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
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.databinding.ItemCastBinding
import project.dheeraj.cinewatch.utils.CONSTANTS

/**
 * Created by Dheeraj Kotwani on 26-03-2021.
 */
@ExperimentalCoroutinesApi
class CastRecyclerViewAdapter(
    val context : Context,
    val castList : ArrayList<Cast>
) : RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCastBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            if (position == 0) {
                binding.spacingStart.visibility = View.VISIBLE
            }
            else if (position == Math.min(20, castList.size)-1) {
                binding.spacingEnd.visibility = View.VISIBLE
            }
            else {
                binding.spacingEnd.visibility = View.GONE
                binding.spacingStart.visibility = View.GONE
            }

            binding.castImage.load(CONSTANTS.ImageBaseURL + castList[position].profile_path) {
                placeholder(CONSTANTS.actorPlaceHolder[position%4])
                error(CONSTANTS.actorPlaceHolder[position%4])
            }

            binding.castName.text = castList[position].name

            binding.root.setOnClickListener {
                val bundle = bundleOf(CONSTANTS.cast to castList[position])
                it.findNavController().navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int = Math.min(20, castList.size)

}