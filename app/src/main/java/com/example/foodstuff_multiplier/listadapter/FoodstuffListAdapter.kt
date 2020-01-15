package com.example.foodstuff_multiplier.listadapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.foodstuff_multiplier.Foodstuff
import com.example.foodstuff_multiplier.R
import com.example.foodstuff_multiplier.toSimpleString

class FoodstuffListAdapter(
    val activity: Activity,
    val foodstuffList: List<Foodstuff>,
    val onClickMainFoddstuff: (index: Int) -> Unit
) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            convertView ?: activity.layoutInflater.inflate(R.layout.item_foodstuff_list, null)
        if (view == null) {
            Log.d("ListView", "View is null")
        }

        val nameTextView: TextView =
            view.findViewById(R.id.foodstuffNameTextView)
        nameTextView.text = foodstuffList[position].name

        val amountTextView: TextView =
            view.findViewById(R.id.amountTextView)
        val amount = foodstuffList[position].amount
        amountTextView.text =
            if (amount > 0f) foodstuffList[position].amount.toSimpleString() else ""

        val unitTextView: TextView =
            view.findViewById(R.id.unitTextView)
        unitTextView.text = foodstuffList[position].unit

        return view
    }

    override fun getItem(p0: Int): Any {
        return foodstuffList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return foodstuffList.size
    }
}