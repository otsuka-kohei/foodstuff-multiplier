package com.example.foodstuff_multiplier.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.foodstuff_multiplier.R
import kotlinx.android.synthetic.main.fragment_input_amount.*

/**
 * A simple [Fragment] subclass.
 */
class InputAmountFragment : Fragment() {

    private val args: InputAmountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_amount, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dish = args.dish

        foodstuffNameTextView.text = dish.foodstuffList[dish.mainFoodstuffIndex].name
        unitTextView.text = dish.foodstuffList[dish.mainFoodstuffIndex].unit

        amountNextButton.setOnClickListener {
            var amount: Float

            try {
                amount = scaleAmountEditText.text.toString().toFloat()
            } catch (e: NumberFormatException) {
                amount = 0f
            }

            val action =
                InputAmountFragmentDirections.actionInputAmountFragmentToScaleResultFragment(
                    dish,
                    amount
                )
            findNavController().navigate(action)
        }
    }

}
