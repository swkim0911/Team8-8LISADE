package com.softeer.togeduck.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun showErrorToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun recordErrLog(tag: String, message: String) {
    Log.e(tag, "failure: ${message}")
}