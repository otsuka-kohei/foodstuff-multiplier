package com.otk1fd.foodstuff_multiplier

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Foodstuff(
    val name: String = "",
    val amount: Float = 0f,
    val unit: String = ""
) : Parcelable