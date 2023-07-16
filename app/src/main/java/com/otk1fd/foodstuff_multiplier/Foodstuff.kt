package com.otk1fd.foodstuff_multiplier

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Foodstuff(
    val name: String = "",
    val amount: Float = 0f,
    val unit: String = ""
) :Parcelable