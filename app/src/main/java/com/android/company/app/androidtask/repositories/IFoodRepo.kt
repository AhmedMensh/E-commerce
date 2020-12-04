package com.android.company.app.androidtask.repositories

import com.android.company.app.androidtask.models.DataResult
import com.android.company.app.androidtask.models.Foods

interface IFoodRepo {

    suspend fun getAllFoods() : DataResult<Foods>
}