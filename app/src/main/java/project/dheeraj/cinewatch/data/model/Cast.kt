package project.dheeraj.cinewatch.data.model

/**
 * Created by Dheeraj Kotwani on 19-03-2021.
 */
class Cast (
    val id: Int,
    val name: String,
    val original_name: String,
    val adult: Boolean,
    val character: String,
    val profile_path: String?,
    val gender: Int,
    val known_for_department: String,
    val popularity: Number,
    val credit_id: String,
    val department: String,
    val job: String
)