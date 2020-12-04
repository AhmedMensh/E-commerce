package com.android.company.app.androidtask.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.android.company.app.androidtask.R
import com.android.company.app.androidtask.ui.foods.FoodsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity()  {

    private val viewModel : FoodsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handelApiError()
        toggleProgressBar()
    }

    private fun toggleProgressBar() {

        viewModel.isLoading.observe(this, Observer {
            it?.let {
                if (it) progressBar.visibility  = View.VISIBLE
                else progressBar.visibility  = View.GONE
            }
        })

    }

    private fun handelApiError() {

        viewModel.error.observe(this, Observer {
            it?.let {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getFoodSearchResult(query: String){
        viewModel.getAllFoods(query).observe(this, Observer {

            it?.let {
                viewModel.updateFoodSearchResult(it)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(searchQueryTextListener)
        return super.onCreateOptionsMenu(menu)
    }


    private var searchQueryTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty())
                getFoodSearchResult(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        }


}
