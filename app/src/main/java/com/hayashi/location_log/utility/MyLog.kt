package com.hayashi.location_log.utility

import android.util.Log

class MyLog(tag: String) {
    private val appPrefix = "----"
    private val tag = "$appPrefix $tag"

    fun d(msg: String?) {
        msg?.let {
            Log.d(tag, it)
        }
    }
}