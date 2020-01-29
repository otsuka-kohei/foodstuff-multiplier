package com.otk1fd.foodstuff_multiplier.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.otk1fd.foodstuff_multiplier.Foodstuff

import com.otk1fd.foodstuff_multiplier.R
import com.otk1fd.foodstuff_multiplier.listadapter.ScaleAmountListAdapter
import kotlinx.android.synthetic.main.fragment_scale_amount.*

/**
 * A simple [Fragment] subclass.
 */
class ScaleAmountFragment : Fragment() {

    private val args: ScaleAmountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scale_amount, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        finishButton.setOnClickListener {
            findNavController().navigate(R.id.action_scaleAmountFragment_to_dishListFragment)
        }

        val dish = args.dish

        var foodstuffList: List<Foodstuff>
        if (dish.serves > 0) {
            val serves = Foodstuff("人数", dish.serves.toFloat(), "人前")
            foodstuffList = listOf(serves).plus(dish.foodstuffList)
        } else {
            foodstuffList = dish.foodstuffList
        }

        val adjustAmountListAdapter =
            ScaleAmountListAdapter(activity!!, foodstuffList)
        adjustAmountListView.adapter = adjustAmountListAdapter
    }


}
