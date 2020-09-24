package com.example.kittytime.application

import timber.log.Timber

class KittyDebugTree: Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "KITTY_$tag", message, t)
    }
}