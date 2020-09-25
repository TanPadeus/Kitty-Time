package com.example.kittytime.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import com.example.kittytime.utils.ConnectionWatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartViewModel @Inject constructor(_context: Context, _connectionWatcher: ConnectionWatcher) {
    val isInternetAvailable: LiveData<Boolean> = _connectionWatcher.isInternetAvailable

    init {
        val cm: ConnectivityManager = _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(NetworkRequest.Builder().build(), _connectionWatcher)
    }
}