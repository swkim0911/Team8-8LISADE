package com.softeer.togeduck.utils

import android.content.res.Resources


fun Int.fromDpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()
