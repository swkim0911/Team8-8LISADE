package com.softeer.togeduck.utils

import android.content.res.Resources
import java.text.DecimalFormat


fun Int.fromDpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.addCommas(): String {
    val dec = DecimalFormat("#,###")

    return dec.format(this)
}