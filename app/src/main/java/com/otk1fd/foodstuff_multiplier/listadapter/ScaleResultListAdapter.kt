package com.otk1fd.foodstuff_multiplier.listadapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.otk1fd.foodstuff_multiplier.R

class ScaleResultListAdapter(
    val activity: Activity,
    val resultList: List<Pair<String, String>>
) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            convertView ?: activity.layoutInflater.inflate(R.layout.item_scale_result_list, null)
        if (view == null) {
            Log.d("ListView", "View is null")
        }

        val foodstuffNameTextView: TextView =
            view.findViewById(R.id.foodstuffNameTextView)
        foodstuffNameTextView.text = resultList[position].first

        val amountTextView: TextView =
            view.findViewById(R.id.amountTextView)
        amountTextView.text = resultList[position].second

        return view
    }

    override fun getItem(p0: Int): Any {
        return resultList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return resultList.size
    }
}