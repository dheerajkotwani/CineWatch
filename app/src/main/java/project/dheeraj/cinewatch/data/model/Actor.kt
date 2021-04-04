package project.dheeraj.cinewatch.data.model

import org.json.JSONObject

/**
 * Created by Dheeraj Kotwani on 19-03-2021.
 */
data class Actor (
    val id: Int,
    val popularity: Number,
    val name: String,
    val birthday: String,
    val also_known_as: ArrayList<String>,
    val biography: String,
    val place_of_birth: String,
    val profile_path: String,
    val known_for_department: String,
    val movie_credits: JSONObject
)