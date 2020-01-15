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
import com.example.foodstuff_multiplier.toSimpleString

class AdjustAmountListAdapter(
    val activity: Activity,
    val foodstuffList: List<Foodstuff>,
    val amountList: List<Float>
) :
    BaseAdapter() {

    val mutableFoodstuffList = foodstuffList.toMutableList()
    val editTextMap = mutableMapOf<Int, EditText>()

    var updaingEditText = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // メモリには優しくないがViewの使いまわし（convertView）を使うと表示が崩れるので仕方がない
        val view = activity.layoutInflater.inflate(R.layout.item_adjust_amount_list, null)

        val foodstuffNameTextView: TextView = view.findViewById(R.id.foodstuffNameTextView)
        foodstuffNameTextView.text = foodstuffList[position].name

        val amountEditText: EditText = view.findViewById(R.id.scaleAmountEditText)
        if (foodstuffList[position].amount > 0f) {
            amountEditText.setText(amountList[position].toSimpleString())
        } else {
            amountEditText.visibility = View.GONE
        }
        if (!editTextMap.keys.contains(position)) {
            editTextMap[position] = amountEditText
        }

        val unitTextView: TextView = view.findViewById(R.id.unitTextView)
        unitTextView.text = foodstuffList[position].unit

        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (updaingEditText) {
                    return
                }

                var amount: Float
                try {
                    amount = amountEditText.text.toString().toFloat()
                    amount = Math.abs(amount)
                } catch (e: NumberFormatException) {
                    return
                }

                val scale = amount / foodstuffList[position].amount

                updaingEditText = true
                editTextMap.map {
                    if (it.key != position) {
                        it.value.setText((foodstuffList[it.key].amount * scale).toSimpleString())
                    }
                }
                updaingEditText = false

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

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
}