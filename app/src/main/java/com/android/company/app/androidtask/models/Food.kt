package com.android.company.app.androidtask.models

typealias Foods = List<Food>
data class Food(
    val id: Int? = null,
    val name: String? = null,
    val price: Float? = null,
    val url: String? = null
)