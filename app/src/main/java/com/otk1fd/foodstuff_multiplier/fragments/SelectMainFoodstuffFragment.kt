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
import com.otk1fd.foodstuff_multiplier.listadapter.SelectMainFoodstuffListAdapter
import kotlinx.android.synthetic.main.fragment_select_main_foodstuff.*

class SelectMainFoodstuffFragment : Fragment() {

    private val args: SelectMainFoodstuffFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_main_foodstuff, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val foodstuffList = args.foodstuffList.toList()
        val calculatableFoodstuffList = foodstuffList.filter { it.amount > 0f }
        val incalculatableFoodstuffList = foodstuffList.filter { it.amount == 0f }

        val selectMainFoodstuffListAdapter =
            SelectMainFoodstuffListAdapter(
                activity!!,
                calculatableFoodstuffList
            ) {
                val dish = Dish(
                    args.id,
                    args.dishName,
                    it,
                    calculatableFoodstuffList.plus(incalculatableFoodstuffList)
                )

                val action =
                    SelectMainFoodstuffFragmentDirections.actionSelectMainFoodstuffFragmentToConfirmDishFragment(
                        dish
                    )
                findNavController().navigate(action)
            }
        selectMainFoodstuffListView.adapter = selectMainFoodstuffListAdapter

    }
}
