package com.software.buy3c

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class MyApplication : Application() {

    companion object {
        var screenWidth: Int = 0
        var screenHeight: Int = 0
        var ref: DatabaseReference? = null
    }
    private var app: MyApplication? = null

    fun getInstance(): MyApplication? {
        return app
    }

    override fun onCreate() {
        super.onCreate()
        ref = FirebaseDatabase.getInstance().reference
        app = this
        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
    }
}