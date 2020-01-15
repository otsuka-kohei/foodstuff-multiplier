package com.example.foodstuff_multiplier.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.foodstuff_multiplier.R
import kotlinx.android.synthetic.main.fragment_input_dish_name.*

class InputDishNameFragment : Fragment() {

    private val args: InputDishNameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_dish_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        args.dish?.let {
            dishNameEditText.setText(it.name)
        }

        dishNameNextButton.setOnClickListener {
            val dishName = dishNameEditText.text.toString()

            if (dishName.isEmpty()) {
                dishNameEditText.error = "料理名を入力してください。"
            } else {
                val action =
                    InputDishNameFragmentDirections.actionInputDishNameFragmentToInputFoodstuffFragment(
                        dishName,
                        args.dish,
                        args.id
                    )
                findNavController().navigate(action)
            }
        }
    }
}
