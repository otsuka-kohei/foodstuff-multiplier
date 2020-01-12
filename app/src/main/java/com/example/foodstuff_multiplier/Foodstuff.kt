package com.example.foodstuff_multiplier

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Foodstuff(
    val name: String,
    val amount: Float,
    val unit: String
) : Parcelable