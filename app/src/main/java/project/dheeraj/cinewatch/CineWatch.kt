package project.dheeraj.cinewatch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Dheeraj Kotwani on 17-03-2021.
 */

@HiltAndroidApp
class CineWatch : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}