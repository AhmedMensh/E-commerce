package com.android.company.app.androidtask.repositories

import com.android.company.app.androidtask.data.remote.RemoteDataSource
import com.android.company.app.androidtask.models.DataResult
import com.android.company.app.androidtask.models.Foods

class FoodRepo(private val remoteDataSource: RemoteDataSource) : IFoodRepo {
    override suspend fun getAllFoods(): DataResult<Foods> {

        return when(val result = remoteDataSource.getAllFoods()){
            is DataResult.Success -> DataResult.Success(result.content)
            is DataResult.Error -> DataResult.Error(result.exception)
        }
    }
}