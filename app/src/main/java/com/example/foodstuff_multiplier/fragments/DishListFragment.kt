package com.example.foodstuff_multiplier.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.foodstuff_multiplier.Dish
import com.example.foodstuff_multiplier.FmSQLiteOpenHelper
import com.example.foodstuff_multiplier.Foodstuff

import com.example.foodstuff_multiplier.R
import com.example.foodstuff_multiplier.listadapter.DishListAdapter
import kotlinx.android.synthetic.main.fragment_dish_list.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class DishListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addDishButton.setOnClickListener {
            val action = DishListFragmentDirections.actionDishListFragmentToInputDishNameFragment(-1)
            findNavController().navigate(action)
        }

        clearTempFoodstuffList()


        val dataList: List<Pair<Int, String>> = FmSQLiteOpenHelper.readAllData()
        val dishList = dataList.map {
            Json.parse(Dish.serializer(), it.second)
        }

        if (dishList.isNotEmpty()) {
            dishListView.visibility = View.VISIBLE
            noItem.visibility = View.GONE
            val dishListAdapter = DishListAdapter(activity!!, dishList)
            dishListView.adapter = dishListAdapter
        }else{
            dishListView.visibility = View.GONE
            noItem.visibility = View.VISIBLE
        }
    }

    private fun clearTempFoodstuffList() {
        val emptyFoodstuffItem = Foodstuff("", 0f, "")

        val defaultFoodstuffList = listOf(emptyFoodstuffItem, emptyFoodstuffItem)

        val jsonData: String = Json.stringify(Foodstuff.serializer().list, defaultFoodstuffList)

        activity?.let {
            val preference = it.applicationContext.getSharedPreferences(
                "temp_foodstuff_list",
                Context.MODE_PRIVATE
            )
            val editor = preference.edit()
            editor.putString("temp_foodstuff_list", jsonData)
            editor.apply()
        }
    }
}
