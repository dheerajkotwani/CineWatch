package project.dheeraj.cinewatch.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import project.dheeraj.cinewatch.data.local.BookmarkDatabase
import javax.inject.Singleton

/**
 * Created by Dheeraj Kotwani on 14-04-2021.
 */

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = BookmarkDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideBookmarkDao(database: BookmarkDatabase) = database.getBookmarkDao()

}