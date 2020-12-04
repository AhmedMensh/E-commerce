package com.android.company.app.androidtask.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.company.app.androidtask.R
import com.android.company.app.androidtask.ui.foods.FoodsViewModel
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity()  {

    private val viewModel : FoodsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getAllFoods().observe(this, Observer {

            it?.let {
                viewModel.updateFoodSearchResult(it)
            }
        })

    }

}
