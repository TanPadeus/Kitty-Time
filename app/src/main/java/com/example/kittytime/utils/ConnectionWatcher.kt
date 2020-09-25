package com.example.kittytime.utils

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

class ConnectionWatcher: ConnectivityManager.NetworkCallback() {
    val isAvailable = MutableLiveData<Boolean>()

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Timber.d("Internet is available.")
        isAvailable.postValue(true)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        Timber.d("Internet is not available.")
        isAvailable.postValue(false)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Timber.d("Internet connection lost.")
        isAvailable.postValue(false)
    }

    companion object {
        private val instance = ConnectionWatcher()
        fun getInstance() = instance
    }
}