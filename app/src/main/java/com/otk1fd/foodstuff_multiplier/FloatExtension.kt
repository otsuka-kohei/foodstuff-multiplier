package com.otk1fd.foodstuff_multiplier

fun Float.toSimpleString(): String {
    if (this.toInt().toFloat() == this) {
        return this.toInt().toString()
    }

    return String.format("%.1f", this)
}