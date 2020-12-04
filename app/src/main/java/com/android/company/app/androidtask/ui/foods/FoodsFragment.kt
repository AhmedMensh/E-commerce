package com.android.company.app.androidtask.ui.foods

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.company.app.androidtask.R
import kotlinx.android.synthetic.main.fragment_foods.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [FoodsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodsFragment : Fragment(R.layout.fragment_foods) {

    private val foodsAdapter : FoodsAdapter by lazy { FoodsAdapter() }
    private val viewModel : FoodsViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFoodSearchResult()
        scrollToTop()
    }

    private fun scrollToTop() {
        fabBtn.setOnClickListener { foodsRV.smoothScrollToPosition(0) }
    }

    private fun getFoodSearchResult() {
        viewModel.getFoodSearchResult().observe(viewLifecycleOwner, Observer {

            it?.let {
                welcomeTV.visibility = View.GONE
                foodsRV.setHasFixedSize(true)
                foodsRV.adapter = foodsAdapter
                foodsRV.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
                foodsAdapter.updateQuerySearch(viewModel.querySearch.value ?: "")
                foodsAdapter.submitList(it)

            }
        })
    }
}