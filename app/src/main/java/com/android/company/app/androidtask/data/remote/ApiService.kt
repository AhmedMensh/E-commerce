package com.android.company.app.androidtask.data.remote

import com.android.company.app.androidtask.models.ApiResponse
import com.android.company.app.androidtask.models.Foods
import retrofit2.http.GET


interface ApiService {


    @GET("foods")
    suspend fun getAllFoods() : ApiResponse<Foods>

}







