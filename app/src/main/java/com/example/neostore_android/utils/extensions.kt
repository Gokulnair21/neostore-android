package com.example.neostore_android.utils

import android.text.Editable


fun String.toPriceFormat(): String {
    if (this.length > 3) {
        return when (this.length) {
            4 -> "${this[0]},${this[1]}${this[2]}${this[3]}"
            5 -> "${this[0]}${this[1]},${this[2]}${this[3]}${this[4]}"
            6 -> "${this[0]},${this[1]}${this[2]},${this[3]}${this[4]}${this[5]}"
            7 -> "${this[0]}${this[1]},${this[2]}${this[3]},${this[4]}${this[5]}${this[6]}"
            else -> this
        }
    }
    return this
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)