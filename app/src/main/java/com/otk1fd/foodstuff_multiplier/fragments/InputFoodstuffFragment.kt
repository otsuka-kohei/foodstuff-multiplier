package com.otk1fd.foodstuff_multiplier.fragments

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
import com.otk1fd.foodstuff_multiplier.Dish
import com.otk1fd.foodstuff_multiplier.listadapter.InputFoodstuffListAdapter
import com.otk1fd.foodstuff_multiplier.Foodstuff

import com.otk1fd.foodstuff_multiplier.databinding.FragmentInputFoodstuffBinding
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class InputFoodstuffFragment : Fragment() {

    private lateinit var addFoodstuffListAdapter: InputFoodstuffListAdapter

    private val args: InputFoodstuffFragmentArgs by navArgs()

    private lateinit var binding: FragmentInputFoodstuffBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInputFoodstuffBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emptyFoodstuffItem = Foodstuff("", 0f, "")
        val editFoodstuffList =
            args.dish.foodstuffList.toMutableList().apply { add(emptyFoodstuffItem) }
        val tempFoodstuffList = loadTempFoodstuffList()

        addFoodstuffListAdapter =
            InputFoodstuffListAdapter(
                requireActivity(),
                tempFoodstuffList ?: editFoodstuffList
            ) {
                addNewFoodstuffItem()
            }
        binding.inputFoodstuffListview.adapter = addFoodstuffListAdapter

        binding.nextButton.setOnClickListener {
            val foodstuffList: List<Foodstuff> =
                addFoodstuffListAdapter.getFoodstuffItemList()
            val validFoodStuffList: List<Foodstuff> =
                foodstuffList.filter { it.name.isNotEmpty() && it.unit.isNotEmpty() }

            val incompleteFoodStuffList =
                foodstuffList.filter { it.name.isNotEmpty() xor it.unit.isNotEmpty() }

            if (validFoodStuffList.isNotEmpty()) {
                val calculableFoodstuffList = validFoodStuffList.filter { it.amount > 0f }
                val incalculableFoodstuffList = validFoodStuffList.filter { it.amount == 0f }

                val dish = Dish(
                    args.dish.id,
                    args.dish.name,
                    args.dish.serves,
                    calculableFoodstuffList.plus(incalculableFoodstuffList)
                )

                val action =
                    InputFoodstuffFragmentDirections.actionInputFoodstuffFragmentToConfirmDishFragment(
                        dish
                    )

                if (incompleteFoodStuffList.isNotEmpty()) {
                    val dialog = ConfirmIncompleteFoodstuffDialog(requireActivity()) {
                        findNavController().navigate(action)
                    }
                    dialog.show(requireActivity().supportFragmentManager, null)
                } else {
                    findNavController().navigate(action)
                }
            } else {
                val dialog = AddFoodstuffValidationDialog(requireActivity())
                dialog.show(requireActivity().supportFragmentManager, null)
            }
        }
    }

    private fun addNewFoodstuffItem() {
        val currentFoodstuffList: MutableList<Foodstuff> =
            addFoodstuffListAdapter.getFoodstuffItemList().toMutableList()

        val emptyFoodstuffItem = Foodstuff("", 0f, "")
        currentFoodstuffList.add(emptyFoodstuffItem)
        addFoodstuffListAdapter =
            InputFoodstuffListAdapter(
                requireActivity(),
                currentFoodstuffList
            ) {
                addNewFoodstuffItem()
            }
        binding.inputFoodstuffListview.adapter = addFoodstuffListAdapter
        binding.inputFoodstuffListview.setSelection(currentFoodstuffList.size + 1)
    }

    override fun onPause() {
        super.onPause()

        Log.d("defaultFoodstuffList", "save state")
        val foodstuffList: List<Foodstuff> =
            addFoodstuffListAdapter.getFoodstuffItemList()
        saveTempFoodstuffList(foodstuffList)
    }

    private fun saveTempFoodstuffList(foodstuffList: List<Foodstuff>) {

        val jsonData: String = Json.encodeToString(ListSerializer(Foodstuff.serializer()), foodstuffList)

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
                    foodstuffList = Json.decodeFromString(ListSerializer(Foodstuff.serializer()), jsonData)
                } catch (e: SerializationException) {
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

class ConfirmIncompleteFoodstuffDialog(
    val activity: Activity,
    val okFun: () -> Unit
) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("入力が完了していない材料があります。\nそれらの項目は追加されませんがよろしいですか？")
        builder.setPositiveButton("はい") { _, _ ->
            okFun()
        }
        builder.setNegativeButton("いいえ", null)
        return builder.create()
    }
}
