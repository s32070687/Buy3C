package com.software.buy3c.ui.activity

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.reflect.TypeToken
import com.software.buy3c.R
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.ui.adapter.HotCampingAdapter
import com.software.buy3c.ui.adapter.ShoppingCarAdapter
import com.software.buy3c.util.Constants
import com.software.buy3c.util.Utility

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/28.
 * #主維護
 * #副維護
 */
class ShoppingCarActivity : AppCompatActivity() {

    private var rvProdList: RecyclerView? = null
    private var mAdapter: ShoppingCarAdapter? = null
    private var btCheckout: Button? = null
    private var prodParam: ProdData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_car)
        setBar()
        setView()
        getData()
    }

    private fun setBar() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        this.supportActionBar?.setDisplayShowHomeEnabled(false)
        this.supportActionBar?.setDisplayShowCustomEnabled(true)
        val mInflater = LayoutInflater.from(this)
        val actionbar = mInflater.inflate(R.layout.actionbar_main, null)
        (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.shopping_car_title)
        this.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))
        val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
        btnActionBack.visibility = View.VISIBLE
        btnActionBack.setOnClickListener{
            finish()
        }

        val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
        btnActionCar.visibility = View.INVISIBLE
    }

    private fun setView() {
        rvProdList = findViewById(R.id.rv_prod_list)
        btCheckout = findViewById(R.id.bt_checkout)

        mAdapter = ShoppingCarAdapter(this)

        rvProdList?.layoutManager = LinearLayoutManager(this)
        rvProdList?.adapter = mAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        val dataString = Utility.getStringValueForKey(this, Constants.LOGIN_DATA)
        val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
        Log.e("Jason","${resultObj?.proData?.size} size")
        resultObj?.proData?.let { mAdapter?.setData(it) }
    }

    override fun onBackPressed() {
        finish()
    }
}