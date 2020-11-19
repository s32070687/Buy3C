package com.software.buy3c.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.software.buy3c.MainActivity
import com.software.buy3c.MyApplication
import com.software.buy3c.R
import com.software.buy3c.api.ApiClientBuilder
import com.software.buy3c.api.gson.AllData
import com.software.buy3c.util.Constants
import com.software.buy3c.util.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/11/19.
 * #主維護
 * #副維護
 */
class WelcomeActivity : AppCompatActivity() , Animation.AnimationListener {

    private var ivIcon: ImageView? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        database = Firebase.database.reference
        getAllData()
    }

    private fun getAllData() {
        val call = ApiClientBuilder.createApiClient().getAllData()
        call.enqueue(object : Callback<AllData> {
            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
                MyApplication.mAllData = response.body()
                setView()
            }

            override fun onFailure(call: Call<AllData>, t: Throwable) {
                val builder = AlertDialog.Builder(this@WelcomeActivity)
                builder.setTitle(R.string.app_name)
                builder.setMessage("網路連線異常，起確認網路連線")
                builder.setPositiveButton("確定",
                    DialogInterface.OnClickListener { _, _ ->
                    })
                builder.setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        })
    }

    private fun setView() {
        Log.e("Jason","setView")
        ivIcon = findViewById(R.id.iv_icon)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.welcome_anime)
        animation.isFillEnabled = true
        animation.fillAfter = true
        animation.setAnimationListener(this)
        ivIcon?.animation = animation
        ivIcon?.startAnimation(animation)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setMessage("確定離開Buy3C嗎?")
        builder.setPositiveButton("確定",
            DialogInterface.OnClickListener { _, _ ->
                val isLoginStatus = Utility.getStringValueForKey(this, Constants.REMEMBER_LOGIN)
                if (isLoginStatus == "false") {
                    Utility.saveStringValueForKey(this, Constants.LOGIN_DATA, null)
                }
                this.finish()
            })
        builder.setNegativeButton("取消",
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onAnimationStart(animation: Animation?) {
        Log.e("Jason","onAnimationStart")
        //動畫開始
    }

    override fun onAnimationRepeat(p0: Animation?) {
        Log.e("Jason","onAnimationRepeat")
    }

    override fun onAnimationEnd(animation: Animation?) {
        //動畫結束
        Log.e("Jason","onAnimationEnd")
        startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
        finish()
    }
}