package com.example.foodstuff_multiplier.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.example.foodstuff_multiplier.R
import com.example.foodstuff_multiplier.listadapter.FoodstuffListAdapter
import kotlinx.android.synthetic.main.fragment_confirm_dish.*

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
        mainFoodstuffTextView.text = dish.foodstuffList[dish.mainFoodstuffIndex].name

        val foodstuffListAdapter = FoodstuffListAdapter(activity!!, dish.foodstuffList) {

        }
        foodstuffListView.adapter = foodstuffListAdapter
    }


}
