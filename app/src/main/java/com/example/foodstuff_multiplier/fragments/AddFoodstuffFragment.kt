package com.example.foodstuff_multiplier.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodstuff_multiplier.AddFoodstuffListAdapter
import com.example.foodstuff_multiplier.FoodstuffItem

import com.example.foodstuff_multiplier.R
import kotlinx.android.synthetic.main.fragment_add_foodstuff.*

class AddFoodstuffFragment : Fragment() {

    private lateinit var addFoodstuffListAdapter: AddFoodstuffListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_foodstuff, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val emptyFoodstuffItem = FoodstuffItem("", 0f, "")
        val defaultFoodstuffList = listOf(emptyFoodstuffItem, emptyFoodstuffItem)
        addFoodstuffListAdapter = AddFoodstuffListAdapter(activity!!, defaultFoodstuffList) {
            addNewFoodstuffItem()
        }
        addFoodStuffListView.adapter = addFoodstuffListAdapter
    }

    fun addNewFoodstuffItem() {
        val currentFoodstuffList: MutableList<FoodstuffItem> =
            addFoodstuffListAdapter.getFoodStufItemList()

        val emptyFoodstuffItem = FoodstuffItem("", 0f, "")
        currentFoodstuffList.add(emptyFoodstuffItem)
        addFoodstuffListAdapter =
            AddFoodstuffListAdapter(activity!!, currentFoodstuffList) {
                addNewFoodstuffItem()
            }
        addFoodStuffListView.adapter = addFoodstuffListAdapter
        addFoodStuffListView.setSelection(currentFoodstuffList.size + 1)
    }
}
