package com.software.buy3c

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.software.buy3c.api.gson.AllData
import com.software.buy3c.ui.LoadingDialog

@Suppress("DEPRECATION")
class MyApplication : Application() {

    companion object {
        var screenWidth: Int = 0
        var screenHeight: Int = 0
        var ref: DatabaseReference? = null
        var mAllData: AllData? = null
    }
    private var app: MyApplication? = null
    private var mProgressDialog: LoadingDialog? = null

    fun getInstance(): MyApplication? {
        return app
    }

    fun showProgressDialog(mCtx: Context?) {
        mCtx?.let {
            val iActivity = it as? Activity
            iActivity?.run {
                if (isFinishing) {
                    return@run
                }
                if (mProgressDialog == null || !mProgressDialog!!.isShowing) {
                    mProgressDialog = LoadingDialog(mCtx)
                    mProgressDialog!!.show()
                }
            }
        }
    }

    fun closeProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog!!.isShowing) {

                mProgressDialog!!.dismiss()
            }
        } catch (e: IllegalArgumentException) {
        } catch (e: Exception) {
        } finally {
            mProgressDialog = null
        }
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