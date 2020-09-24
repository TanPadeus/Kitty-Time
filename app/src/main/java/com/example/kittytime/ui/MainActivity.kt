package com.example.kittytime.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kittytime.R
import com.example.kittytime.utils.ConnectionWatcher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}