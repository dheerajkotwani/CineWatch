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
        val vote_count: Int,
        val runtime: Int,
        val poster_path: String,
        val overview: String,
        val release_date: String,
        val title: String,
        val backdrop_path: String,
        val popularity: Number,
        val vote_average: Number,
        val genre_ids: List<Int>,
        val genre_names: List<String>,
        val genres: @RawValue List<Genre>,
        val video : Boolean,
        val videos: @RawValue Video
) : Parcelable