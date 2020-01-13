package com.example.foodstuff_multiplier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.foodstuff_multiplier.listadapter.DishListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dish_list.*
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationController = findNavController(navigationHostFragment)
        val appBarConfiguration = AppBarConfiguration(navigationController.graph)
        setupActionBarWithNavController(navigationController, appBarConfiguration)

        FmSQLiteOpenHelper.setup(this)
    }

    override fun onSupportNavigateUp() = findNavController(navigationHostFragment).navigateUp()

    override fun onDestroy() {
        super.onDestroy()

        FmSQLiteOpenHelper.teardown()
    }
}
