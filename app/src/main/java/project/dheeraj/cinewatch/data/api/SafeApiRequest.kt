package project.dheeraj.cinewatch.data.api

import retrofit2.Response
import timber.log.Timber.e

/**
 * Created by Dheeraj Kotwani on 21-03-2021.
 */
abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call : suspend() -> Response<T>): T {

        e("Api Request")
        val response = call.invoke()

        if (response.isSuccessful && response.body() != null) {
            e(response.body().toString())
            return response.body()!!
        }
        else {
            e(response.code().toString())
            e(response.message())
            throw Exception(response.code().toString())
        }

    }

}