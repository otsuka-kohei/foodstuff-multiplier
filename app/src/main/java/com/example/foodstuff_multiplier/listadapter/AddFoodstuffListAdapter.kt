package com.example.foodstuff_multiplier.listadapter

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.foodstuff_multiplier.Foodstuff
import com.example.foodstuff_multiplier.R

class AddFoodstuffListAdapter(
    val activity: Activity,
    val foodstuffList: List<Foodstuff>,
    val onClickAddFoddstuff: () -> Unit
) :
    BaseAdapter() {

    val mutableFoodstuffList = foodstuffList.toMutableList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // メモリには優しくないがViewの使いまわし（convertView）を使うと表示が崩れるので仕方がない
        val view = activity.layoutInflater.inflate(R.layout.item_add_foodstuff_list, null)

        val foodstuffNameEditText: EditText = view.findViewById(R.id.foodstuffNameEditText)
        foodstuffNameEditText.setText(foodstuffList[position].name)

        val amountEditText: EditText = view.findViewById(R.id.scaleAmountEditText)
        if (foodstuffList[position].amount > 0f) {
            amountEditText.setText(foodstuffList[position].amount.toString())
        }

        val unitEditText: EditText = view.findViewById(R.id.unitEditText)
        unitEditText.setText(foodstuffList[position].unit)

        val addNewStuffTextView: TextView = view.findViewById(R.id.addNewStuffTextView)
        addNewStuffTextView.setOnClickListener {
            onClickAddFoddstuff()
        }

        foodstuffNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                updateFoodstuffList(position, foodstuffNameEditText, amountEditText, unitEditText)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                updateFoodstuffList(position, foodstuffNameEditText, amountEditText, unitEditText)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        unitEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                updateFoodstuffList(position, foodstuffNameEditText, amountEditText, unitEditText)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        val inputStuffLayout: ConstraintLayout = view.findViewById(R.id.inputStuffLayout)
        val addNewStuffLayout: ConstraintLayout = view.findViewById(R.id.addNewStuffLayout)
        if (position == foodstuffList.size - 1) {
            inputStuffLayout.visibility = View.GONE
            addNewStuffLayout.visibility = View.VISIBLE
        } else {
            inputStuffLayout.visibility = View.VISIBLE
            addNewStuffLayout.visibility = View.GONE
        }

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

    fun getFoodStufItemList(): List<Foodstuff> {
        return mutableFoodstuffList.toList()
    }

    private fun updateFoodstuffList(
        index: Int,
        nameEditText: EditText,
        amountEditText: EditText,
        unitEditText: EditText
    ) {
        val name = nameEditText.text.toString()

        var amount: Float
        try {
            val amountText: String = amountEditText.text.toString()
            amount =
                if (amountText.isNotEmpty()) amountEditText.text.toString().toFloat() else 0f
        } catch (e: NumberFormatException) {
            amount = 0f
        }

        val unit = unitEditText.text.toString()

        val foodstuffItem =
            Foodstuff(name, amount, unit)
        mutableFoodstuffList[index] = foodstuffItem

        Log.d(
            "update foodstuff list",
            index.toString() + ":" + name + ", " + amount.toString() + ", " + unit
        )
    }
}