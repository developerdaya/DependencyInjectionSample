package com.curve.delivery.util

import android.content.Context
import android.net.ConnectivityManager

//--------------------------------Define NetworkUtils Object:: DeveloperUpendra--------------------------------//
object NetworkUtil {
    fun isConnectionAvailable(context: Context): Boolean {
        var status = false
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            status = netInfo != null && netInfo.isConnected
            status
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
