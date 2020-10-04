package com.example.buy3c

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.view.WindowManager

@Suppress("DEPRECATION")
class MyApplication : Application() {
    companion object {
        var screenWidth: Int = 0
        var screenHeight: Int = 0
    }
    private var app: MyApplication? = null

    fun getInstance(): MyApplication? {
        return app
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
    }
}