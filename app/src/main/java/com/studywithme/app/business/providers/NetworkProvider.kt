package com.studywithme.app.business.providers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build

class NetworkProvider(private val context: Context) : INetworkProvider {

    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cm.allNetworks
                    .mapNotNull(cm::getNetworkCapabilities)
                    .any { capabilities ->
                        capabilities.hasCapability(NET_CAPABILITY_INTERNET)
                    }
        } else {
            cm.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}
