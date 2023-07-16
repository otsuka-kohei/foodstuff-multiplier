package com.otk1fd.foodstuff_multiplier.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.otk1fd.foodstuff_multiplier.Dish
import com.otk1fd.foodstuff_multiplier.FmSQLiteOpenHelper
import com.otk1fd.foodstuff_multiplier.R
import com.otk1fd.foodstuff_multiplier.databinding.ActivityMainBinding
import com.otk1fd.foodstuff_multiplier.databinding.FragmentConfirmDishBinding
import com.otk1fd.foodstuff_multiplier.databinding.FragmentDishListBinding
import com.otk1fd.foodstuff_multiplier.listadapter.DishListAdapter
import kotlinx.serialization.json.Json


class DishListFragment : Fragment() {

    private lateinit var dishList: List<Dish>

    private lateinit var binding: FragmentDishListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDishListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addDishButton.setOnClickListener {
            val newId = getNewDishId()
            Log.d("DishListFragment", "new ID : $newId")
            if (newId >= 0) {
                val newDish = Dish(id = newId)
                val action =
                    DishListFragmentDirections.actionDishListFragmentToInputDishInfoFragment(newDish)
                findNavController().navigate(action)
            }
        }

        clearTempFoodstuffList()

        setDishDataToListView()

        binding.dishListview.setOnItemClickListener { _, _, position, _ ->
            val action =
                DishListFragmentDirections.actionDishListFragmentToAdjustAmountFragment(dishList[position])
            findNavController().navigate(action)
        }

        registerForContextMenu(binding.dishListview)
    }

    private fun setDishDataToListView() {
        val dbDataList: List<Pair<Int, String>> = FmSQLiteOpenHelper.readAllData(requireContext())
        dishList = dbDataList.map {
            Json.decodeFromString(Dish.serializer(), it.second)
        }

        if (dishList.isNotEmpty()) {
            binding.dishListview.visibility = View.VISIBLE
            binding.noItemMessage.visibility = View.GONE

            dishList = dishList.sortedWith(compareBy(Dish::name))
            val dishListAdapter = DishListAdapter(requireActivity(), dishList)
            binding.dishListview.adapter = dishListAdapter
        } else {
            binding.dishListview.visibility = View.GONE
            binding.noItemMessage.visibility = View.VISIBLE
        }
    }

    private fun getNewDishId(): Int {

        val idList = FmSQLiteOpenHelper.readDataIdList(requireContext())
        for (newId in 0..Int.MAX_VALUE) {
            if (!idList.contains(newId)) {
                return newId
            }
        }
        return -1
    }

    private fun clearTempFoodstuffList() {
        activity?.let {
            val preference = it.applicationContext.getSharedPreferences(
                "temp_foodstuff_list",
                Context.MODE_PRIVATE
            )
            val editor = preference.edit()
            editor.putString("temp_foodstuff_list", "")
            editor.apply()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        activity?.menuInflater?.inflate(R.menu.dish_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val adapterContextMenuInfo: AdapterView.AdapterContextMenuInfo =
            item.menuInfo as AdapterView.AdapterContextMenuInfo

        val position = adapterContextMenuInfo.position

        if (item.itemId == R.id.dish_list_edit) {
            val dish = dishList[position]
            val action =
                DishListFragmentDirections.actionDishListFragmentToInputDishInfoFragment(dish = dish)
            findNavController().navigate(action)

        } else if (item.itemId == R.id.dish_list_delete) {
            val dish = dishList[position]
            val dialog = ConfirmDeleteDishDialog(requireActivity(), dish) {
                setDishDataToListView()
            }
            dialog.show(parentFragmentManager, null)
        }

        return super.onContextItemSelected(item)
    }
}

class ConfirmDeleteDishDialog(
    val activity: Activity,
    val dish: Dish,
    val afterDeleteFun: () -> Unit
) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("項目「${dish.name}」を削除してもよろしいですか？")
        builder.setPositiveButton("はい") { _, _ ->
            FmSQLiteOpenHelper.deleteData(requireContext(), dish.id)
            afterDeleteFun()
        }
        builder.setNegativeButton("いいえ", null)
        return builder.create()
    }
}
