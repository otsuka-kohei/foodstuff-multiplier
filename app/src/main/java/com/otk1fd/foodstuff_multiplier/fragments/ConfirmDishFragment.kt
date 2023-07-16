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
import com.otk1fd.foodstuff_multiplier.databinding.ActivityMainBinding
import com.otk1fd.foodstuff_multiplier.databinding.FragmentConfirmDishBinding
import com.otk1fd.foodstuff_multiplier.databinding.FragmentDishListBinding
import com.otk1fd.foodstuff_multiplier.listadapter.FoodstuffListAdapter
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify

class ConfirmDishFragment : Fragment() {

    private val args: ConfirmDishFragmentArgs by navArgs()

    private lateinit var binding: FragmentConfirmDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConfirmDishBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dish = args.dish
        binding.dishNameTextView.text = dish.name
        binding.servesTextView.text = dish.serves.toString()

        val foodstuffListAdapter = FoodstuffListAdapter(requireActivity(), dish.foodstuffList) {

        }
        binding.foodstuffListView.adapter = foodstuffListAdapter

        binding.confirmButton.setOnClickListener {
            val dbDataIdList = FmSQLiteOpenHelper.readDataIdList(requireContext())
            if (dbDataIdList.contains(dish.id)) {
                FmSQLiteOpenHelper.updateData(
                    requireContext(),
                    dish.id,
                    Json.encodeToString(Dish.serializer(), dish)
                )
            } else {
                FmSQLiteOpenHelper.appendData(
                    requireContext(),
                    dish.id,
                    Json.encodeToString(Dish.serializer(), dish)
                )
            }
            findNavController().navigate(R.id.action_confirmDishFragment_to_dishListFragment)
        }
    }


}
