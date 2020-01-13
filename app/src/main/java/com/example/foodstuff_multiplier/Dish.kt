package com.example.foodstuff_multiplier

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Dish(
    val id: Int,
    val name: String,
    val mainFoodstuffIndex: Int,
    val foodstuffList: List<Foodstuff>
) : Parcelable