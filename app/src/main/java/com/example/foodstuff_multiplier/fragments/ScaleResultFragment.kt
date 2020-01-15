package com.example.foodstuff_multiplier.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.foodstuff_multiplier.R
import com.example.foodstuff_multiplier.listadapter.ScaleResultListAdapter
import com.example.foodstuff_multiplier.toSimpleString
import kotlinx.android.synthetic.main.fragment_scale_result.*

class ScaleResultFragment : Fragment() {

    private val args: ScaleResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scale_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        finishButton.setOnClickListener {
            findNavController().navigate(R.id.action_scaleResultFragment_to_dishListFragment)
        }

        val dish = args.dish
        val scale = args.mainFoodstuffAmount / dish.foodstuffList[dish.mainFoodstuffIndex].amount
        val results: List<Pair<String, String>> = dish.foodstuffList.map {
            val amount =
                if (it.amount > 0f) "${(it.amount * scale).toSimpleString()} ${it.unit}" else it.unit
            Pair(it.name, amount)
        }
        val scaleResultListAdapter = ScaleResultListAdapter(activity!!, results)
        scaleResultListView.adapter = scaleResultListAdapter


        val scaledAmountList: List<Float> = dish.foodstuffList.map { it.amount * scale }
        adjustButton.setOnClickListener {
            val action =
                ScaleResultFragmentDirections.actionScaleResultFragmentToAdjustAmountFragment(
                    args.dish,
                    scaledAmountList.toFloatArray()
                )
            findNavController().navigate(action)
        }
    }


}
