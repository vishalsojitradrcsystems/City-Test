package com.city.test.util

import android.content.Context
import java.io.IOException

fun Context.readJsonFromAssets(fileName: String): String? {
    try {
        return assets.open(fileName).bufferedReader().use { it.readText()}
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}