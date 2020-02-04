package com.otk1fd.foodstuff_multiplier.listadapter

import android.app.Activity
import android.graphics.Color
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

        if (position % 2 == 0) {
            view.setBackgroundResource(R.color.listItem)
        }

        val dish = dishList[position]

        val dishNameTextView: TextView =
            view.findViewById(R.id.dishNameTextView)
        dishNameTextView.text = dish.name

        val foodstuffTextView: TextView =
            view.findViewById(R.id.foodstuffTextView)
        var foodstuffText = ""
        dish.foodstuffList.forEach {
            if (foodstuffText.isNotEmpty()) {
                foodstuffText += ", "
            }
            foodstuffText += it.name
        }
        foodstuffTextView.text = foodstuffText

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