package com.software.buy3c.ui.activity

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.software.buy3c.R
import com.software.buy3c.api.gson.CampingData

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
    private var campingParam: CampingData? = null

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
        }
    }

    private fun setView() {
        ivCamping = findViewById(R.id.iv_camping)
        tvPrice = findViewById(R.id.tv_price_text)
        tvDiscountPrice = findViewById(R.id.tv_discount)
        tvCampingName = findViewById(R.id.tv_detail_text)

        tvPrice?.paint?.flags = Paint. STRIKE_THRU_TEXT_FLAG
    }

    private fun setData() {
        if (intent != null) {
            campingParam = intent.getSerializableExtra("campingData") as? CampingData
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
                tvCampingName?.text = it.name
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}