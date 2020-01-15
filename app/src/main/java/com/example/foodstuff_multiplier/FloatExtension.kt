package com.example.foodstuff_multiplier

fun Float.toSimpleString(): String {
    if (this.toInt().toFloat() == this) {
        return this.toInt().toString()
    }
    return this.toString()
}