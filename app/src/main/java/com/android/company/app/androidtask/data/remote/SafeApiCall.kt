package com.android.company.app.androidtask.data.remote

import android.util.Log
import com.android.company.app.androidtask.models.ApiError
import com.android.company.app.androidtask.models.ApiResponse
import com.android.company.app.androidtask.models.DataResult
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException


val moshi: Moshi = Moshi.Builder().build()
val jsonAdapter: JsonAdapter<ApiError> = moshi.adapter(ApiError::class.java)

suspend fun <T> safeApiCall(apiCall: suspend () -> ApiResponse<T>): DataResult<T> {

    return try {
        val result = apiCall()
        if (result.success == true) {
            val data = result.data
            return DataResult.Success(data)
        }

        return DataResult.Error(Exception("Something went wrong please try again"))
    } catch (e: Exception) {
        Log.i("Error", "${e.message}")
        when (e) {
            is HttpException -> {
                val errorBodyString = e.response()?.errorBody()?.string()
                var errorBodyJson: ApiError? = null
                if (errorBodyString != null) {
                    try {
                        errorBodyJson = jsonAdapter.fromJson(errorBodyString)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                when (e.code()) {
                    in 300 until 400 -> {
                        DataResult.Error(Exception("Unauthorized"))
                    }
                    in 400 until 500 -> {
                        DataResult.Error(
                            Exception(
                                errorBodyJson?.message
                                    ?: "Something went wrong please try again"
                            )
                        )
                    }
                    in 500 until 600 -> {
                        DataResult.Error(Exception("Server error please try again."))
                    }
                    else -> {
                        DataResult.Error(Exception("Something went wrong please try again"))
                    }
                }
            }
            is IOException -> {
                DataResult.Error(Exception("No internet connection."))
            }
            else -> {
                DataResult.Error(Exception("Something went wrong please try again"))
            }
        }
    }
}
