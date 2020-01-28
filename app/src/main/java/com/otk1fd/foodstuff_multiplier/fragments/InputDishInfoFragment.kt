package com.otk1fd.foodstuff_multiplier.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.otk1fd.foodstuff_multiplier.R
import kotlinx.android.synthetic.main.fragment_input_dish_info.*

class InputDishInfoFragment : Fragment() {

    private val args: InputDishInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_dish_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        args.dish?.let {
            dishNameEditText.setText(it.name)
        }

        dishNameNextButton.setOnClickListener {
            val dishName = dishNameEditText.text.toString()

            var serves: Int
            try {
                val servesText: String = servesEditText.text.toString()
                serves =
                    if (servesText.isNotEmpty()) servesEditText.text.toString().toInt() else 0
                serves = Math.abs(serves)
            } catch (e: NumberFormatException) {
                serves = 0
            }

            if (dishName.isEmpty()) {
                dishNameEditText.error = "料理名を入力してください。"
            } else {
                val action =
                    InputDishInfoFragmentDirections.actionInputDishInfoFragmentToInputFoodstuffFragment(
                        dishName,
                        args.dish,
                        args.id,
                        serves
                    )
                findNavController().navigate(action)
            }
        }
    }
}
