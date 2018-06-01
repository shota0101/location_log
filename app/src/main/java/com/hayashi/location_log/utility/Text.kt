package com.hayashi.location_log.utility

import android.content.Context
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import java.io.*

class Text(filePath: String, context: Context) {
    private val context: Context = context
    private val filePath: String = filePath

    fun append(text: String) {
        val out: OutputStream
        try {
            out = this.context.openFileOutput(filePath, MODE_PRIVATE or MODE_APPEND)
            val writer = PrintWriter(OutputStreamWriter(out, "UTF-8"))
            writer.append(text) // 追記する
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun read(): String {
        val input: InputStream
        var text = ""
        try {
            input = this.context.openFileInput(this.filePath)
            val reader = BufferedReader(InputStreamReader(input, "UTF-8"))
            text = reader.readText()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return text
    }
}