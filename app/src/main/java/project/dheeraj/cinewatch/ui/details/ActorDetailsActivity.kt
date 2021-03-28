package project.dheeraj.cinewatch.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.databinding.ActivityActorDetailsBinding

class ActorDetailsActivity : AppCompatActivity() {


    private lateinit var binding : ActivityActorDetailsBinding
    private lateinit var viewModel : ActorDetailsViewModel

    companion object {

        private var personId = 0

        fun start(context : Context, personId : Int) {
            context.startActivity(Intent(context, ActorDetailsActivity::class.java))
            this.personId = personId
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActorDetailsBinding.inflate(layoutInflater)
        setContentView(binding.recyclerViewBestMovies)

        viewModel = ViewModelProvider(this).get(ActorDetailsViewModel::class.java)

        viewModel.getPerson(personId).observe(this, Observer {
            val actor = it.data
            if (actor != null) {
                binding.textActorName.text = actor.name
            }
        })

    }
}