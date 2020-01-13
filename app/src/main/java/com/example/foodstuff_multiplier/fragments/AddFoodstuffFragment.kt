package com.example.foodstuff_multiplier.fragments

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodstuff_multiplier.AddFoodstuffListAdapter
import com.example.foodstuff_multiplier.Foodstuff

import com.example.foodstuff_multiplier.R
import kotlinx.android.synthetic.main.fragment_add_foodstuff.*

class AddFoodstuffFragment : Fragment() {

    private lateinit var addFoodstuffListAdapter: AddFoodstuffListAdapter

    private val args: AddFoodstuffFragmentArgs by navArgs()

    private var addNewDish: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_foodstuff, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val emptyFoodstuffItem = Foodstuff("", 0f, "")

        if (args.dishName != null) {
            addNewDish = true
        }

        addFoodstuffNextButton.setOnClickListener {
            val foodstuffList: MutableList<Foodstuff> =
                addFoodstuffListAdapter.getFoodStufItemList()
            val validFoodStuffList: List<Foodstuff> =
                foodstuffList.filter { it.name.isNotEmpty() && it.unit.isNotEmpty() }

            if (validFoodStuffList.isNotEmpty()) {
                val dishName = args.dishName ?: args.dish!!.name
                if (dishName != null) {
                    val action =
                        AddFoodstuffFragmentDirections.actionAddFoodstuffFragmentToSelectMainFoodstuffFragment(
                            dishName, validFoodStuffList.toTypedArray()
                        )
                    findNavController().navigate(action)
                }
            } else {
                val dialog = AddFoodstuffValidationDialog(activity!!)
                dialog.show(fragmentManager!!, null)
            }
        }

        val defaultFoodstuffList: List<Foodstuff> =
            args.dish?.foodstuffList ?: listOf(emptyFoodstuffItem, emptyFoodstuffItem)

        addFoodstuffListAdapter = AddFoodstuffListAdapter(activity!!, defaultFoodstuffList) {
            addNewFoodstuffItem()
        }
        addFoodStuffListView.adapter = addFoodstuffListAdapter
    }

    fun addNewFoodstuffItem() {
        val currentFoodstuffList: MutableList<Foodstuff> =
            addFoodstuffListAdapter.getFoodStufItemList()

        val emptyFoodstuffItem = Foodstuff("", 0f, "")
        currentFoodstuffList.add(emptyFoodstuffItem)
        addFoodstuffListAdapter =
            AddFoodstuffListAdapter(activity!!, currentFoodstuffList) {
                addNewFoodstuffItem()
            }
        addFoodStuffListView.adapter = addFoodstuffListAdapter
        addFoodStuffListView.setSelection(currentFoodstuffList.size + 1)
    }
}

class AddFoodstuffValidationDialog(val activity: Activity) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("少なくとも1つ以上の材料名と単位を入力してください。")
        builder.setPositiveButton("OK", null)
        return builder.create()
    }
}
