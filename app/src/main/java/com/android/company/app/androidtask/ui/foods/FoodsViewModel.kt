package com.android.company.app.androidtask.ui.foods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.company.app.androidtask.models.DataResult
import com.android.company.app.androidtask.models.Foods
import com.android.company.app.androidtask.repositories.IFoodRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodsViewModel(private val iFoodRepo: IFoodRepo) : ViewModel() {

    private var foodSearchResult = MutableLiveData<Foods>()
    fun updateFoodSearchResult(foods : Foods) {
        foodSearchResult.value = foods
    }
    fun getFoodSearchResult() = foodSearchResult
    fun getAllFoods() : MutableLiveData<Foods?> {

        val data = MutableLiveData<Foods?>()

        viewModelScope.launch {
            when(val result = withContext(Dispatchers.IO) { iFoodRepo.getAllFoods()}){
                is DataResult.Success -> data.value = result.content
                is DataResult.Error -> data.value = null
            }
        }
        return data
    }
}