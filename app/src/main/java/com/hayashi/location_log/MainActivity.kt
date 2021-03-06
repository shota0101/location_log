package com.hayashi.location_log

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.hayashi.location_log.utility.MyLog
import com.hayashi.location_log.utility.Text
import java.util.*


class MainActivity : AppCompatActivity() {

    private val l = MyLog("----")
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        addRap()

        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    private fun start() {
        l.d("MapsActivity")
        LocationListenerImpl.activity = this
        LocationListenerImpl.text = Text("location_log.csv", this)
        LocationStarter.start(this)
    }

    fun setText(text: String) {
        if (this.textView == null)
            this.textView = findViewById(R.id.text_view)
        this.textView?.let {
            it.text = text
        }
    }

    fun addRap() {
        LocationListenerImpl.text?.let {
            it.append("${Date()},rap\n")
        }
    }
}
