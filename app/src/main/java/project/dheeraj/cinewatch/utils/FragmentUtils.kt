package project.dheeraj.cinewatch.utils

import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by Dheeraj Kotwani on 20-03-2021.
 */

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

/**
 * Returns Color from resource.
 * @param id Color Resource ID
 */
fun Fragment.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(requireContext(), id)
