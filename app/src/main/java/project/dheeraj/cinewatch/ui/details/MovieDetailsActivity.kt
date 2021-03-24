package project.dheeraj.cinewatch.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.utils.showToast

class MovieDetailsActivity : AppCompatActivity() {

    companion object {

        private const val MOVIE_ID = "movie_id";
        private var movie: Movie? = null

        fun getStartIntent(context : Context, movie : Movie) {
            context.startActivity(Intent(context, MovieDetailsActivity::class.java))
            this.movie = movie
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        
        showToast(movie!!.title)
        
    }
}