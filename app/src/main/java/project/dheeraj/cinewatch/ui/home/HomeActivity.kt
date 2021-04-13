package project.dheeraj.cinewatch.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.dheeraj.cinewatch.R

class HomeActivity : AppCompatActivity() {

    companion object {

        fun start(context : Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}