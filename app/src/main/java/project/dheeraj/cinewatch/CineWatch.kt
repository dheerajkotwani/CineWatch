package project.dheeraj.cinewatch

import android.app.Application
import timber.log.Timber

/**
 * Created by Dheeraj Kotwani on 17-03-2021.
 */

class CineWatch : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}