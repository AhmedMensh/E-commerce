package com.android.company.app.androidtask.ui.foods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.company.app.androidtask.models.DataResult
import com.android.company.app.androidtask.models.Foods
import com.android.company.app.androidtask.repositories.FoodRepo
import com.android.company.app.androidtask.repositories.IFoodRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FoodsViewModel(private val iFoodRepo: IFoodRepo) : ViewModel() {

    val error = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()

    var querySearch = MutableLiveData<String?>()

    private var foodSearchResult = MutableLiveData<Foods>()
    fun updateFoodSearchResult(foods: Foods) {
        foodSearchResult.value = foods
    }

    fun getFoodSearchResult() = foodSearchResult


    fun getAllFoods(query: String): MutableLiveData<Foods?> {
        querySearch.value = query
        val data = MutableLiveData<Foods?>()
        isLoading.value = true

        viewModelScope.launch {
            when (val result = withContext(Dispatchers.IO) { iFoodRepo.getAllFoods() }) {
                is DataResult.Success -> {
                    delay(200)
                    data.value = result.content?.filter {
                        it.name!!.contains(
                            query.trim(),
                            ignoreCase = true
                        )
                    }?.take(10)
                    isLoading.value = false
                    error.value = null
                }
                is DataResult.Error -> {
                    data.value = null
                    isLoading.value = false
                    error.value = null
                }
            }
        }
        return data
    }
}