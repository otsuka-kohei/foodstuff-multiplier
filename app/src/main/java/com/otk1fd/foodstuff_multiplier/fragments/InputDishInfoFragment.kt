package com.otk1fd.foodstuff_multiplier.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.otk1fd.foodstuff_multiplier.Dish

import com.otk1fd.foodstuff_multiplier.R
import com.otk1fd.foodstuff_multiplier.databinding.FragmentDishListBinding
import com.otk1fd.foodstuff_multiplier.databinding.FragmentInputDishInfoBinding
import kotlin.math.abs

class InputDishInfoFragment : Fragment() {

    private val args: InputDishInfoFragmentArgs by navArgs()

    private lateinit var binding: FragmentInputDishInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInputDishInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.dish.name.isNotEmpty()) {
            binding.dishNameEditText.setText(args.dish.name)
        }

        if (args.dish.serves != -1) {
            binding.servesEditText.setText(args.dish.serves.toString())
        }

        binding.dishNameNextButton.setOnClickListener {
            val dishName = binding.dishNameEditText.text.toString()

            if (dishName.isEmpty()) {
                binding.dishNameEditText.error = "料理名を入力してください。"
                return@setOnClickListener
            }

            val serves: Int =
                try {
                    val servesText: String = binding.servesEditText.text.toString()

                    val tempServes =
                        if (servesText.isNotEmpty())
                            binding.servesEditText.text.toString().toInt()
                        else
                            0

                    abs(tempServes)
                } catch (e: NumberFormatException) {
                    0
                }


            val action =
                InputDishInfoFragmentDirections.actionInputDishInfoFragmentToInputFoodstuffFragment(
                    Dish(
                        args.dish.id,
                        dishName,
                        serves,
                        args.dish.foodstuffList
                    )
                )
            findNavController().navigate(action)
        }
    }
}
