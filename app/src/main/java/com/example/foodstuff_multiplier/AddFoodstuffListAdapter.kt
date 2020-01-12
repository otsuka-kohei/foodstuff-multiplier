package com.example.foodstuff_multiplier

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText

class AddFoodstuffListAdapter(
    val activity: Activity,
    val foodstuffList: List<FoodstuffItem>,
    val onClickAddFoddstuff: () -> Unit
) :
    BaseAdapter() {

    val viewList = mutableListOf<View>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            convertView ?: activity.layoutInflater.inflate(R.layout.item_foodstuff_list, null)
        if (view == null) {
            Log.d("ListView", "View is null")
        }

        val foodstuffNameEditText: EditText = view.findViewById(R.id.foodstuffNameEditText)
        foodstuffNameEditText.setText(foodstuffList[position].name)

        val amountEditText: EditText = view.findViewById(R.id.amountEditText)
        amountEditText.setText(foodstuffList[position].amount.toString())

        val unitEditText: EditText = view.findViewById(R.id.unitEditText)
        unitEditText.setText(foodstuffList[position].unit)

        viewList.add(view)

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

    fun getFoodStufItemList(): List<FoodstuffItem> {
        val list = mutableListOf<FoodstuffItem>()

        for (view in viewList) {
            val nameEditText: EditText = view.findViewById(R.id.foodstuffNameEditText)
            val name = nameEditText.text.toString()

            val amountEditText: EditText = view.findViewById(R.id.amountEditText)
            val amount = amountEditText.text.toString().toFloat()

            val unitEditText: EditText = view.findViewById(R.id.unitEditText)
            val unit = unitEditText.text.toString()

            val foodstuffItem = FoodstuffItem(name, amount, unit)
            list.add(foodstuffItem)
        }

        return list
    }
}