package com.android.company.app.androidtask.ui.foods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.company.app.androidtask.R
import com.android.company.app.androidtask.common.highLight
import com.android.company.app.androidtask.common.setIcon
import com.android.company.app.androidtask.models.Food
import kotlinx.android.synthetic.main.list_item_food.view.*


class FoodsAdapter : ListAdapter<Food,FoodsAdapter.ViewHolder>(DiffCallback){

    private var query = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position),query)
    }

    fun updateQuerySearch(querySearch: String) {
        query = querySearch
    }


    object DiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }

    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

         fun bind(item: Food, query: String){
             itemView.apply {

                 item.url?.setIcon(foodImgV)
                 foodNameTV.text = item.name
                 foodPriceTV.text = "${item.price} ${itemView.resources.getString(R.string.currency)}"
                 foodNameTV.highLight(query)
             }
         }
     }


}
