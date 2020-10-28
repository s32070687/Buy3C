package com.software.buy3c.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.software.buy3c.R
import com.software.buy3c.api.ApiClientBuilder
import com.software.buy3c.api.gson.AllData
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.api.gson.ProdData
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
 * Created by Jason on 2020/10/27.
 * #主維護
 * #副維護
 */
class RegisterActivity : AppCompatActivity() {

    private var etAcc: EditText? = null
    private var etPsw: EditText? = null
    private var etPswConfirm: EditText? = null
    private var etName: EditText? = null
    private var btRegister: Button? = null
    private var ref: DatabaseReference? = null
    private var mAllData: AllData? = null
    private var mData: ArrayList<MemberData>? = null
    private var mProdData: ArrayList<ProdData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setBar()
        setView()
        getMemberData()
    }

    private fun setBar() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        this.supportActionBar?.setDisplayShowHomeEnabled(false)
        this.supportActionBar?.setDisplayShowCustomEnabled(true)
        val mInflater = LayoutInflater.from(this)
        val actionbar = mInflater.inflate(R.layout.actionbar_main, null)
        (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.register_title)
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
        etAcc = findViewById(R.id.et_reg_acc)
        etPsw = findViewById(R.id.et_reg_psw)
        etPswConfirm = findViewById(R.id.et_reg_psw_confirm)
        etName = findViewById(R.id.et_reg_name)
        btRegister = findViewById(R.id.bt_register)

        btRegister?.setOnClickListener {
            checkRegister()
        }
    }

    private fun getMemberData() {
        val call = ApiClientBuilder.createApiClient().getAllData()
        call.enqueue(object : Callback<AllData> {

            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
                mAllData = response.body()
            }

            override fun onFailure(call: Call<AllData>, t: Throwable) {
                Log.d("response", "${t.message}")
            }
        })
    }

    private fun checkRegister() {
        if (TextUtils.isEmpty(etAcc?.text) || TextUtils.isEmpty(etPsw?.text) || TextUtils.isEmpty(etPswConfirm?.text) || TextUtils.isEmpty(etName?.text)) {
            Toast.makeText(this, "資料需填寫完整", Toast.LENGTH_SHORT).show()
        } else {
            if (etPsw?.text.toString() == etPswConfirm?.text.toString()) {
                startRegister()
            } else {
                Toast.makeText(this, "密碼與確認密碼不同", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRegister() {
        if (mAllData?.MemberData == null) {
            ref = FirebaseDatabase.getInstance().reference.child("AllData").child("MemberData")
            mData = ArrayList<MemberData>()
            mProdData = ArrayList<ProdData>()
            val memberData = MemberData()
            val prodData = ProdData()
            memberData.acc = etAcc?.text.toString()
            memberData.psw = etPsw?.text.toString()
            memberData.name = etName?.text.toString()
            memberData.proData?.add(prodData)
            mData!!.add(memberData)

            ref?.setValue(mData)
            Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show()
            toMember(memberData)
        } else {
            for (i in 0 until mAllData!!.MemberData?.size!!) {
                if (mAllData!!.MemberData?.get(i)?.acc == etAcc?.text.toString()) {
                    Toast.makeText(this, "已有相同的帳號", Toast.LENGTH_SHORT).show()
                    break
                } else {
                    ref = FirebaseDatabase.getInstance().reference.child("AllData").child("MemberData")
                    mData = ArrayList<MemberData>()
                    mProdData = ArrayList<ProdData>()
                    val memberData = MemberData()
                    memberData.acc = etAcc?.text.toString()
                    memberData.psw = etPsw?.text.toString()
                    memberData.name = etName?.text.toString()
//                    val childUpdates = hashMapOf<String, Any>(
//                        "MemberData" to memberData
//                    )

                    ref?.setValue(memberData)
                    Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show()
                    toMember(memberData)
                    break
                }
            }
        }
    }

    private fun toMember(data: MemberData) {
        Log.e("Jason","dada")
        Utility.saveStringValueForKey(this, Constants.LOGIN_DATA, Utility.convertGsonToString(data))
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}