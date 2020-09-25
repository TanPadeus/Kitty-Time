package com.example.kittytime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kittytime.R
import com.example.kittytime.application.KittyApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KittyApp).appComponent.inject(this)
        setContentView(R.layout.activity_main)
    }
}