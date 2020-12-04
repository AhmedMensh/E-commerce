package com.android.company.app.androidtask.models

import com.squareup.moshi.Json

data class ApiResponse<T>(
    @field:Json(name = "data")
    val data: T?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "success")
    val success: Boolean?
)
