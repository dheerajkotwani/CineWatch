package project.dheeraj.cinewatch.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Cast
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.State
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.ActivityActorDetailsBinding
import project.dheeraj.cinewatch.ui.main.adapter.HomeRecyclerViewAdapter
import project.dheeraj.cinewatch.utils.CONSTANTS
import project.dheeraj.cinewatch.utils.showToast

@ExperimentalCoroutinesApi
class ActorDetailsActivity : AppCompatActivity() {


    private lateinit var binding : ActivityActorDetailsBinding
    private lateinit var viewModel : ActorDetailsViewModel
    private var movieCredits : ArrayList<Movie> = ArrayList()
    private lateinit var homeRecyclerViewAdapter : HomeRecyclerViewAdapter

    companion object {

        private var personId = 0

        fun start(context : Context, cast : Cast) {
            context.startActivity(Intent(context, ActorDetailsActivity::class.java))
            this.personId = cast.id
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActorDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ActorDetailsViewModel::class.java)
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(this, movieCredits)
        binding.recyclerViewBestMovies.adapter = homeRecyclerViewAdapter

        viewModel.getPerson(personId).observe(this, Observer {
            val actor = it.data
            if (actor != null) {
                binding.textActorName.text = actor.name
                binding.actorImage.load(CONSTANTS.ImageBaseURL + actor.profile_path)

                var knownAs = ""
                for (i in 0..Math.min(4, actor.also_known_as.size)-1) {
                    knownAs += actor.also_known_as[i]
                    if (i != actor.also_known_as.size-1) {
                        knownAs += ", "
                    }
                }
                binding.textAlsoKnownAs.text = knownAs

                binding.textBio.text = actor.biography

                binding.textPopularity.text = actor.popularity.toString()
                binding.textCharacter.text = actor.known_for_department
                binding.textBirthday.text = actor.birthday
            }
        })

        val skeleton = binding.recyclerViewBestMovies.applySkeleton(R.layout.home_movie_card, 10)

        viewModel.getPersonMovieCredits(personId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    skeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    skeleton.showOriginal()
                    movieCredits.clear()
                    movieCredits.addAll(res.data!!.cast)
                    homeRecyclerViewAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }
}