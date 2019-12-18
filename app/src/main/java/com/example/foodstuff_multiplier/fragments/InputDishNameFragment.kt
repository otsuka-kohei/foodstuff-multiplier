package com.example.foodstuff_multiplier.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.foodstuff_multiplier.R
import kotlinx.android.synthetic.main.fragment_input_dish_name.*

class InputDishNameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_dish_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dishNameNextButton.setOnClickListener {
            findNavController().navigate(R.id.action_inputDishNameFragment_to_addFoodstuffFragment)
        }
    }
}
