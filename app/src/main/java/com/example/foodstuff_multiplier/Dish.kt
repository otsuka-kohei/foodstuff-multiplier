package com.example.foodstuff_multiplier

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dish(
    val name: String,
    val mainFoodstuffIndex: Int,
    val foodstuffList: List<Foodstuff>
) : Parcelable