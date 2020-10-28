package com.software.buy3c.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.reflect.TypeToken
import com.software.buy3c.R
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.util.Constants
import com.software.buy3c.util.Utility

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/25.
 * #主維護
 * #副維護
 */
class CampingActivity : AppCompatActivity() {

    private var ivCamping: ImageView? = null
    private var tvPrice: TextView? = null
    private var tvDiscountPrice: TextView? = null
    private var tvCampingName: TextView? = null
    private var btADD: Button? = null
    private var campingParam: ProdData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camping)
        setBar()
        setView()
        setData()
    }

    private fun setBar() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        this.supportActionBar?.setDisplayShowHomeEnabled(false)
        this.supportActionBar?.setDisplayShowCustomEnabled(true)
        val mInflater = LayoutInflater.from(this)
        val actionbar = mInflater.inflate(R.layout.actionbar_main, null)
        (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.camping_title)
        this.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))
        val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
        btnActionBack.visibility = View.VISIBLE
        btnActionBack.setOnClickListener{
            finish()
        }

        val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
        btnActionCar.visibility = View.VISIBLE
        btnActionCar.setOnClickListener {
            val dataString = Utility.getStringValueForKey(this,Constants.LOGIN_DATA)
            if (dataString != null) {
                intent.setClass(this, ShoppingCarActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "請先登入會員", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setView() {
        ivCamping = findViewById(R.id.iv_camping)
        tvPrice = findViewById(R.id.tv_price_text)
        tvDiscountPrice = findViewById(R.id.tv_discount)
        tvCampingName = findViewById(R.id.tv_detail_text)
        btADD = findViewById(R.id.bt_add)

        tvPrice?.paint?.flags = Paint. STRIKE_THRU_TEXT_FLAG
        btADD?.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage("確定加入購物車嗎?")
            builder.setPositiveButton("確定",
                DialogInterface.OnClickListener { _, _ ->
                    val dataString = Utility.getStringValueForKey(this,Constants.LOGIN_DATA)
                    if (dataString != null) {
                        val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
                        resultObj?.proData = ArrayList<ProdData>()
                        campingParam?.let { it1 -> resultObj?.proData?.add(it1) }
                        Utility.saveStringValueForKey(this, Constants.LOGIN_DATA, Utility.convertGsonToString(resultObj))
                        Toast.makeText(this, "已加入購物車", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "請先登入會員", Toast.LENGTH_SHORT).show()
                    }
                })
            builder.setNegativeButton("取消",
                DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }

    private fun setData() {
        if (intent != null) {
            campingParam = intent.getSerializableExtra("campingData") as? ProdData
            campingParam?.let {
                ivCamping?.let {view ->
                    Glide.with(this)
                        .load(it.imageUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(view)
                }

                tvPrice?.text = it.price.toString()
                tvDiscountPrice?.text = it.discount.toString()
                tvCampingName?.text = it.detail
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}