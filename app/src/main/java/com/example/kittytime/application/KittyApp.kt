package com.example.kittytime.application

import android.app.Application
import timber.log.Timber

class KittyApp: Application() {
    val appComponent: AppComponent by lazy { initializeComponent() }
    
    override fun onCreate() {
        super.onCreate()
        Timber.plant(KittyDebugTree())
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}