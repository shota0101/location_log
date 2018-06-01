package com.hayashi.location_log.extensions

import android.location.Location

fun Location.toCSV(): String {
    return "${this.latitude},${this.longitude}"
}