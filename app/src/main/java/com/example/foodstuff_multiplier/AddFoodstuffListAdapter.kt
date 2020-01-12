package com.example.foodstuff_multiplier

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Exception

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
        if (foodstuffList[position].amount > 0f) {
            amountEditText.setText(foodstuffList[position].amount.toString())
        }

        val unitEditText: EditText = view.findViewById(R.id.unitEditText)
        unitEditText.setText(foodstuffList[position].unit)

        val addNewStuffTextView: TextView = view.findViewById(R.id.addNewStuffTextView)
        addNewStuffTextView.setOnClickListener {
            onClickAddFoddstuff()
        }

        if (position == foodstuffList.size - 1) {
            val inputStuffLayout: ConstraintLayout = view.findViewById(R.id.inputStuffLayout)
            inputStuffLayout.visibility = View.GONE
        } else {
            val addNewStuffLayout: ConstraintLayout = view.findViewById(R.id.addNewStuffLayout)
            addNewStuffLayout.visibility = View.GONE
        }

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

    fun getFoodStufItemList(): MutableList<FoodstuffItem> {
        val list = mutableListOf<FoodstuffItem>()

        for (view in viewList) {
            val nameEditText: EditText = view.findViewById(R.id.foodstuffNameEditText)
            val name = nameEditText.text.toString()

            var amount: Float
            try {
                val amountEditText: EditText = view.findViewById(R.id.amountEditText)
                amount = amountEditText.text.toString().toFloat()
            } catch (e: NumberFormatException) {
                amount = 0f
            }

            val unitEditText: EditText = view.findViewById(R.id.unitEditText)
            val unit = unitEditText.text.toString()

            val foodstuffItem = FoodstuffItem(name, amount, unit)
            list.add(foodstuffItem)
        }

        return list
    }
}