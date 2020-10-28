package com.software.buy3c.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
import org.w3c.dom.Text

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/24.
 * #主維護
 * #副維護
 */
class ProdItemActivity : AppCompatActivity() {

    private var ivProd: ImageView? = null
    private var tvProdName: TextView? = null
    private var tvProdPrice: TextView? = null
    private var tvProdDetail: TextView? = null
    private var btADD: Button? = null
    private var prodParam: ProdData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prod_item)
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
        (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.prod_title)
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
        ivProd = findViewById(R.id.iv_prod)
        tvProdName = findViewById(R.id.tv_prod_name)
        tvProdPrice = findViewById(R.id.tv_price_text)
        tvProdDetail = findViewById(R.id.tv_detail_text)
        btADD = findViewById(R.id.bt_add)

        btADD?.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage("確定加入購物車嗎?")
            builder.setPositiveButton("確定",
                DialogInterface.OnClickListener { _, _ ->
                    val dataString = Utility.getStringValueForKey(this, Constants.LOGIN_DATA)
                    if (dataString != null) {
                        val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
                        resultObj?.proData = ArrayList<ProdData>()
                        prodParam?.let { it1 -> resultObj?.proData?.add(it1) }
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

    @SuppressLint("SetTextI18n")
    private fun setData() {
        if (intent != null) {
            prodParam = intent.getSerializableExtra("prodData") as? ProdData
            prodParam?.let {
                ivProd?.let {view ->
                    Glide.with(this)
                        .load(it.imageUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(view)
                }

                tvProdName?.text = it.name
                tvProdPrice?.text = "$ ${it.discount.toString()}"
                tvProdDetail?.text = it.detail
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}