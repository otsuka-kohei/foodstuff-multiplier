package com.example.foodstuff_multiplier.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodstuff_multiplier.listadapter.AddFoodstuffListAdapter
import com.example.foodstuff_multiplier.Foodstuff

import com.example.foodstuff_multiplier.R
import kotlinx.android.synthetic.main.fragment_add_foodstuff.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecodingException

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

        if (args.id != -1) {
            addNewDish = true
        }

        addFoodstuffNextButton.setOnClickListener {
            Log.d("AddFoodStuffState", addNewDish.toString())

            val foodstuffList: List<Foodstuff> =
                addFoodstuffListAdapter.getFoodStufItemList()
            val validFoodStuffList: List<Foodstuff> =
                foodstuffList.filter { it.name.isNotEmpty() && it.unit.isNotEmpty() }

            if (validFoodStuffList.isNotEmpty()) {
                val dishName = args.dish!!.name
                val id = if (addNewDish) args.id else args.dish!!.id
                val action =
                    AddFoodstuffFragmentDirections.actionAddFoodstuffFragmentToSelectMainFoodstuffFragment(
                        dishName, validFoodStuffList.toTypedArray(), id
                    )
                findNavController().navigate(action)
            } else {
                val dialog = AddFoodstuffValidationDialog(activity!!)
                dialog.show(fragmentManager!!, null)
            }
        }

        val emptyFoodstuffItem = Foodstuff("", 0f, "")
        val defaultFoodstuffList = listOf(emptyFoodstuffItem, emptyFoodstuffItem)
        val editFoodstuffList =
            args.dish?.foodstuffList?.toMutableList()?.apply { add(emptyFoodstuffItem) }
        val tempFoodstuffList = loadTempFoodstuffList()

        if (editFoodstuffList == null) {
            Log.d("AddFoodstuffFragment", "edit is null")
        }

        addFoodstuffListAdapter =
            AddFoodstuffListAdapter(
                activity!!,
                tempFoodstuffList ?: editFoodstuffList ?: defaultFoodstuffList
            ) {
                addNewFoodstuffItem()
            }
        addFoodStuffListView.adapter = addFoodstuffListAdapter
    }

    fun addNewFoodstuffItem() {
        val currentFoodstuffList: MutableList<Foodstuff> =
            addFoodstuffListAdapter.getFoodStufItemList().toMutableList()

        val emptyFoodstuffItem = Foodstuff("", 0f, "")
        currentFoodstuffList.add(emptyFoodstuffItem)
        addFoodstuffListAdapter =
            AddFoodstuffListAdapter(
                activity!!,
                currentFoodstuffList
            ) {
                addNewFoodstuffItem()
            }
        addFoodStuffListView.adapter = addFoodstuffListAdapter
        addFoodStuffListView.setSelection(currentFoodstuffList.size + 1)
    }

    override fun onPause() {
        super.onPause()

        Log.d("defaultFoodstuffList", "save state")
        val foodstuffList: List<Foodstuff> =
            addFoodstuffListAdapter.getFoodStufItemList()
        saveTempFoodstuffList(foodstuffList)
    }

    private fun saveTempFoodstuffList(foodstuffList: List<Foodstuff>) {

        val jsonData: String = Json.stringify(Foodstuff.serializer().list, foodstuffList)

        activity?.let {
            val preference = it.applicationContext.getSharedPreferences(
                "temp_foodstuff_list",
                Context.MODE_PRIVATE
            )
            val editor = preference.edit()
            editor.putString("temp_foodstuff_list", jsonData)
            editor.apply()
        }
    }

    private fun loadTempFoodstuffList(): List<Foodstuff>? {
        activity?.let {
            val preference = it.applicationContext.getSharedPreferences(
                "temp_foodstuff_list",
                Context.MODE_PRIVATE
            )
            val jsonData: String? = preference.getString("temp_foodstuff_list", "")
            jsonData?.let {
                var foodstuffList: List<Foodstuff>?
                try {
                    foodstuffList = Json.parse(Foodstuff.serializer().list, jsonData)
                } catch (e: JsonDecodingException) {
                    foodstuffList = null
                }
                return foodstuffList
            }
        }

        return null
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
