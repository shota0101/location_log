package com.hayashi.location_log

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.TextView
import com.hayashi.location_log.LocationStarter.locationProvider
import com.hayashi.location_log.extensions.toCSV
import com.hayashi.location_log.utility.MyLog
import com.hayashi.location_log.utility.Text
import java.util.*

object LocationListenerImpl : LocationListener {
    private val l = MyLog("----")
    var text: Text? = null
    var activity: MainActivity? = null

    // ロケーションプロバイダが利用可能になるとコールバックされるメソッド
    override fun onProviderEnabled(provider: String) {
        l.d("onProviderEnabled called : provider = $provider")
        locationProvider = provider
    }

    // 位置情報が通知されるたびにコールバックされるメソッド
    override fun onLocationChanged(location: Location) {
        l.d("onLocationChanged called : location = $location")
        text?.let {
            it.append("${Date()},${location.toCSV()}\n")
        }
        activity?.let {
            it.setText(Date().toString())
        }
    }

    // ロケーションステータスが変わるとコールバックされるメソッド
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        l.d("onStatusChanged called : provider = $provider, status = $status, extras = $extras")
    }

    //ロケーションプロバイダが利用不可能になるとコールバックされるメソッド
    override fun onProviderDisabled(provider: String) {
        l.d("onProviderDisabled called : provider = $provider")
    }
}