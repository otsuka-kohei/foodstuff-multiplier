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
import com.otk1fd.foodstuff_multiplier.databinding.FragmentDishListBinding
import com.otk1fd.foodstuff_multiplier.databinding.FragmentScaleAmountBinding
import com.otk1fd.foodstuff_multiplier.listadapter.ScaleAmountListAdapter

/**
 * A simple [Fragment] subclass.
 */
class ScaleAmountFragment : Fragment() {

    private val args: ScaleAmountFragmentArgs by navArgs()

    private lateinit var binding: FragmentScaleAmountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentScaleAmountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finishButton.setOnClickListener {
            findNavController().navigate(R.id.action_scaleAmountFragment_to_dishListFragment)
        }

        val dish = args.dish

        val foodstuffList: List<Foodstuff> = if (dish.serves > 0) {
            val serves = Foodstuff("人数", dish.serves.toFloat(), "人前")
            listOf(serves).plus(dish.foodstuffList)
        } else {
            dish.foodstuffList
        }

        val adjustAmountListAdapter =
            ScaleAmountListAdapter(requireActivity(), foodstuffList)
        binding.adjustAmountListView.adapter = adjustAmountListAdapter
    }


}
