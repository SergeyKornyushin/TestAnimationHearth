package com.example.testanimationhearth

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Расширенный Application
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}
