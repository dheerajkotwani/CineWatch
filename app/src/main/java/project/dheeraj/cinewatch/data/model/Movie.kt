package project.dheeraj.cinewatch.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import org.json.JSONObject
import project.dheeraj.cinewatch.utils.CONSTANTS.Companion.TABLE_NAME

/**
 * Created by Dheeraj Kotwani on 19-03-2021.
 */
@Parcelize
data class Movie (
        val id: Int,
        val vote_count: Int ?= null,
        val runtime: Int ?= null,
        val poster_path: String ?= null,
        val overview: String ?= null,
        val release_date: String ?= null,
        val title: String ?= null,
        val backdrop_path: String ?= null,
        val popularity: Number ?= null,
        val vote_average: Number ?= null,
        val genre_ids: List<Int> ?= null,
        val genre_names: List<String> ?= null,
        val genres: @RawValue List<Genre> ?= null,
        val video : Boolean ?= null,
        val videos: @RawValue Video ?= null
) : Parcelable