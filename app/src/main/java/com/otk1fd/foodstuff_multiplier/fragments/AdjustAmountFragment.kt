package com.otk1fd.foodstuff_multiplier.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.otk1fd.foodstuff_multiplier.R
import com.otk1fd.foodstuff_multiplier.listadapter.AdjustAmountListAdapter
import kotlinx.android.synthetic.main.fragment_adjust_amount.*

/**
 * A simple [Fragment] subclass.
 */
class AdjustAmountFragment : Fragment() {

    private val args: AdjustAmountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adjust_amount, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        finishButton.setOnClickListener {
            findNavController().navigate(R.id.action_adjustAmountFragment_to_dishListFragment)
        }

        val adjustAmountListAdapter =
            AdjustAmountListAdapter(activity!!, args.dish.foodstuffList, args.amountList.toList())
        adjustAmountListView.adapter = adjustAmountListAdapter
    }


}
