package com.android.company.app.androidtask.ui.foods

import com.android.company.app.androidtask.models.Food

interface FoodListener {

    fun addToCart(item : Food)
}