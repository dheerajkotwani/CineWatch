package project.dheeraj.cinewatch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.RawValue
import project.dheeraj.cinewatch.utils.CONSTANTS

/**
 * Created by Dheeraj Kotwani on 14-04-2021.
 */
@Entity(tableName = CONSTANTS.TABLE_NAME)
data class MovieDB (
    @PrimaryKey
    val id: Int,
    val poster_path: String,
    val overview: String,
    val title: String,
    val backdrop_path: String
)