package com.otk1fd.foodstuff_multiplier.listadapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.otk1fd.foodstuff_multiplier.Dish
import com.otk1fd.foodstuff_multiplier.R

class DishListAdapter(
    val activity: Activity,
    val dishList: List<Dish>
) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            convertView ?: activity.layoutInflater.inflate(R.layout.item_dish_list, null)
        if (view == null) {
            Log.d("ListView", "View is null")
        }

        val dish = dishList[position]

        val dishNameTextView: TextView =
            view.findViewById(R.id.dishNameTextView)
        dishNameTextView.text = dish.name

        val mainFoodstuffTextView: TextView =
            view.findViewById(R.id.mainFoodstuffTextView)
        mainFoodstuffTextView.text = dish.foodstuffList[dish.mainFoodstuffIndex].name

        return view
    }

    override fun getItem(p0: Int): Any {
        return dishList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return dishList.size
    }
}