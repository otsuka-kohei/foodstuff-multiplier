package com.example.foodstuff_multiplier.fragments

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
import com.example.foodstuff_multiplier.Dish
import com.example.foodstuff_multiplier.FmSQLiteOpenHelper
import com.example.foodstuff_multiplier.R
import com.example.foodstuff_multiplier.listadapter.DishListAdapter
import kotlinx.android.synthetic.main.fragment_dish_list.*
import kotlinx.serialization.json.Json


class DishListFragment : Fragment() {

    private lateinit var dishList: List<Dish>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addDishButton.setOnClickListener {
            val newId = getNewDishId()
            if (newId >= 0) {
                Log.d("DishListFragment", "new ID : ${newId}")
                val action =
                    DishListFragmentDirections.actionDishListFragmentToInputDishNameFragment(newId)
                findNavController().navigate(action)
            }
        }

        clearTempFoodstuffList()

        setDishDataToListView()

        dishListView.setOnItemClickListener { adapterView, view, position, l ->
            val action =
                DishListFragmentDirections.actionDishListFragmentToInputAmountFragment(dishList[position])
            findNavController().navigate(action)
        }

        registerForContextMenu(dishListView)
    }

    private fun setDishDataToListView() {
        val dbDataList: List<Pair<Int, String>> = FmSQLiteOpenHelper.readAllData()
        dishList = dbDataList.map {
            Json.parse(Dish.serializer(), it.second)
        }

        if (dishList.isNotEmpty()) {
            dishListView.visibility = View.VISIBLE
            noItem.visibility = View.GONE

            dishList = dishList.sortedWith(compareBy(Dish::name))
            val dishListAdapter = DishListAdapter(activity!!, dishList)
            dishListView.adapter = dishListAdapter
        } else {
            dishListView.visibility = View.GONE
            noItem.visibility = View.VISIBLE
        }
    }

    private fun getNewDishId(): Int {
        val idList = FmSQLiteOpenHelper.readDataIdList()
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
                DishListFragmentDirections.actionDishListFragmentToInputDishNameFragment(dish = dish)
            findNavController().navigate(action)

        } else if (item.itemId == R.id.dish_list_delete) {
            val dish = dishList[position]
            val dialog = ConfirmDeleteDishDialog(activity!!, dish) {
                setDishDataToListView()
            }
            dialog.show(fragmentManager!!, null)
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
        builder.setPositiveButton("はい") { dialog, id ->
            FmSQLiteOpenHelper.deleteData(dish.id)
            afterDeleteFun()
        }
        builder.setNegativeButton("いいえ", null)
        return builder.create()
    }
}
