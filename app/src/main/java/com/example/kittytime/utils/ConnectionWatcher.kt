package com.example.kittytime.utils

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionWatcher @Inject constructor(): ConnectivityManager.NetworkCallback(){
    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> = _isInternetAvailable

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Timber.d("Internet is available.")
        _isInternetAvailable.postValue(true)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        Timber.d("Internet is not available.")
        _isInternetAvailable.postValue(false)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Timber.d("Internet connection lost.")
        _isInternetAvailable.postValue(false)
    }

    companion object {
        private val instance = ConnectionWatcher()
        fun getInstance() = instance
    }
}