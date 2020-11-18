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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.reflect.TypeToken
import com.software.buy3c.MyApplication
import com.software.buy3c.R
import com.software.buy3c.api.ApiClientBuilder
import com.software.buy3c.api.gson.AllData
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.util.Constants
import com.software.buy3c.util.Utility
import org.w3c.dom.Text
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
    private var ref: DatabaseReference? = null
//    private var mAllData: AllData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prod_item)
//        getData()
        setBar()
        setView()
        setData()
    }

//    private fun getData() {
//        val call = ApiClientBuilder.createApiClient().getAllData()
//        call.enqueue(object : Callback<AllData> {
//
//            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
//                mAllData = response.body()
//            }
//
//            override fun onFailure(call: Call<AllData>, t: Throwable) {
//                Log.d("response", "${t.message}")
//            }
//        })
//    }

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
            val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
            if (resultObj != null) {
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
            val dataString = Utility.getStringValueForKey(this, Constants.LOGIN_DATA)
            val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
            if (resultObj != null) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.app_name)
                builder.setMessage("確定加入購物車嗎?")
                builder.setPositiveButton("確定",
                    DialogInterface.OnClickListener { _, _ ->
                        update(resultObj.acc,prodParam)
                        if (resultObj.proData == null) {
                            resultObj.proData = ArrayList<ProdData>()
                            prodParam?.let { it1 -> resultObj.proData?.add(it1) }
                        } else {
                            prodParam?.let { it1 -> resultObj.proData?.add(it1) }
                        }
                        Utility.saveStringValueForKey(this, Constants.LOGIN_DATA, Utility.convertGsonToString(resultObj))
                        Toast.makeText(this, "已加入購物車", Toast.LENGTH_SHORT).show()
                    })
                builder.setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
                val alert: AlertDialog = builder.create()
                alert.show()
            } else {
                Toast.makeText(this, "請先登入會員", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun update(acc: String?, prod: ProdData?) {
        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("MemberData")
        for (i in 0 until MyApplication.mAllData?.MemberData?.size!!) {
            if (acc == MyApplication.mAllData?.MemberData?.get(i)?.acc) {
                if (MyApplication.mAllData?.MemberData?.get(i)?.proData != null) {
                    prod?.let { MyApplication.mAllData?.MemberData?.get(i)?.proData?.add(it) }
                } else {
                    MyApplication.mAllData?.MemberData?.get(i)?.proData = ArrayList<ProdData>()
                    prod?.let { MyApplication.mAllData?.MemberData?.get(i)?.proData?.add(it) }
                }
            }
        }
        ref?.setValue(MyApplication.mAllData?.MemberData)
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