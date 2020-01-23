package com.otk1fd.foodstuff_multiplier.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.otk1fd.foodstuff_multiplier.Dish
import com.otk1fd.foodstuff_multiplier.FmSQLiteOpenHelper

import com.otk1fd.foodstuff_multiplier.R
import com.otk1fd.foodstuff_multiplier.listadapter.FoodstuffListAdapter
import kotlinx.android.synthetic.main.fragment_confirm_dish.*
import kotlinx.serialization.json.Json

class ConfirmDishFragment : Fragment() {

    private val args: ConfirmDishFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_dish, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dish = args.dish
        dishNameTextView.text = dish.name
        mainFoodstuffTextView.text = dish.foodstuffList[dish.serves].name

        val foodstuffListAdapter = FoodstuffListAdapter(activity!!, dish.foodstuffList) {

        }
        foodstuffListView.adapter = foodstuffListAdapter

        confirmButton.setOnClickListener {
            val dbDataIdList = FmSQLiteOpenHelper.readDataIdList()
            if (dbDataIdList.contains(dish.id)) {
                FmSQLiteOpenHelper.updateData(dish.id, Json.stringify(Dish.serializer(), dish))
            } else {
                FmSQLiteOpenHelper.appendData(dish.id, Json.stringify(Dish.serializer(), dish))
            }
            findNavController().navigate(R.id.action_confirmDishFragment_to_dishListFragment)
        }
    }


}
