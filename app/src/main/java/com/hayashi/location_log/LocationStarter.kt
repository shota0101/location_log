package com.hayashi.location_log

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import com.hayashi.location_log.utility.MyLog

object LocationStarter {
    private val l = MyLog("----")
    private val minTime: Long = 500 // 位置情報の通知の最小時間間隔（ミリ秒）
    private val minDistance: Long = 1 // 位置情報を通知の最小距離間隔（メートル）
    var locationProvider: String? = null


    fun start(activity: Activity) {
        l.d("start")
        requestPermissionIfNot(activity)

        val locationManager: LocationManager
                = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationProvider = this.getLocationProvider(activity, locationManager)

        locationProvider?.let {
            requestLocationUpdates(
                    LocationListenerImpl,
                    locationManager,
                    it)
        }
    }

    private fun requestPermissionIfNot(activity: Activity) {
        // Fine か Coarseのいずれかのパーミッションが得られているかチェックする
        // 本来なら、Android6.0以上かそうでないかで実装を分ける必要がある
        val isFineGranted = ActivityCompat.checkSelfPermission(activity.application, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val isCoarseGranted = ActivityCompat.checkSelfPermission(activity.application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!isFineGranted && !isCoarseGranted) {
            // fine location のリクエストコード（値は他のパーミッションと被らなければ、なんでも良い）
            val requestCode = 1
            // いずれも得られていない場合はパーミッションのリクエストを要求する
            ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    requestCode)
        }
    }

    private fun requestLocationUpdates(locationListener: LocationListener, locationManager: LocationManager, locationProvider: String) {
        // 利用可能なロケーションプロバイダによる位置情報の取得の開始
        // FIXME 本来であれば、リスナが複数回登録されないようにチェックする必要がある
        try {
            locationManager.requestLocationUpdates(
                    locationProvider,
                    minTime,
                    minDistance.toFloat(),
                    locationListener)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        l.d("requestLocationUpdates success")
    }

    private fun getLocationProvider(activity: Activity, locationManager: LocationManager): String? {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPSが利用可能になっている場合
            l.d("getLocationProvider GPSを利用")
            return LocationManager.GPS_PROVIDER
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // GPSプロバイダーが有効になっていない場合は基地局情報が利用可能になっている場合
            l.d("getLocationProvider 基地局情報を利用")
            return LocationManager.NETWORK_PROVIDER
        } else {
            // いずれも利用可能でない場合は、GPSを設定する画面に遷移
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activity.startActivity(settingsIntent)
            l.d("getLocationProvider GPSを設定する画面に遷移")
        }
        return null
    }

}
