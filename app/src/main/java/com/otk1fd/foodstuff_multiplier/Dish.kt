package com.otk1fd.foodstuff_multiplier

import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize

@Serializable
@Parcelize
data class Dish(
    val id: Int = -1,
    val name: String = "",
    val serves: Int = -1,
    val foodstuffList: List<Foodstuff> = emptyList()
) : Parcelable