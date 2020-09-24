package com.example.kittytime.application

import android.app.Application
import timber.log.Timber

class KittyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(KittyDebugTree())
    }
}