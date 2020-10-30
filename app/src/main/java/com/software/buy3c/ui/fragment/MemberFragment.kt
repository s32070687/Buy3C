package com.software.buy3c.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.reflect.TypeToken
import com.software.buy3c.ui.BaseFragment
import com.software.buy3c.ui.FragmentPageManager
import com.software.buy3c.R
import com.software.buy3c.api.ApiClientBuilder
import com.software.buy3c.api.gson.AllData
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.ui.activity.RegisterActivity
import com.software.buy3c.ui.activity.ShoppingCarActivity
import com.software.buy3c.ui.adapter.AlsoLikeAdapter
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
 * Created by Jason on 2020/10/19.
 * #主維護
 * #副維護
 */
class MemberFragment : BaseFragment() {

    private var mAllData: AllData? = null
    //未登入
    private var rlLogout: RelativeLayout? = null
    private var etAcc: EditText? = null
    private var etPsw: EditText? = null
    private var cbLogin: CheckBox? = null
    private var btLogin: Button? = null
    private var btRegister: Button? = null

    //已登入
    private var rlLogin: RelativeLayout? = null
    private var tvUserName: TextView? = null
    private var btLogout: Button? = null
    private var rvAlsoLike: RecyclerView? = null
    private var mAdapter: AlsoLikeAdapter? = null

    @Suppress("PrivatePropertyName")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("Jason","mem onCreateView")
        return inflater.inflate(R.layout.member_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mFpm = FragmentPageManager(mOwnActivity, fragmentManager)
        setView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        setActionBar()
    }

    override fun onResume() {
        super.onResume()
        val dataString = Utility.getStringValueForKey(mOwnActivity,Constants.LOGIN_DATA)
        if (dataString != null) {
            getData()
            val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
            resultObj?.let { setLogin(it) }
        } else {
            setLogOut()
        }
    }

    @SuppressLint("InflateParams")
    private fun setActionBar() {
        if (mOwnActivity != null) {
            mOwnActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowHomeEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowCustomEnabled(true)
            val mInflater = LayoutInflater.from(mOwnActivity)
            val actionbar = mInflater.inflate(R.layout.actionbar_main, null)
            (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.member_title)
            mOwnActivity?.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))

            val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
            btnActionBack.visibility = View.INVISIBLE

            val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
            btnActionCar.visibility = View.VISIBLE
            btnActionCar.setOnClickListener {
                val intent = Intent()
                mOwnActivity?.let { it1 ->
                    val dataString = Utility.getStringValueForKey(it1,Constants.LOGIN_DATA)
                    if (dataString.isNotEmpty()) {
                        intent.setClass(it1, ShoppingCarActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(it1, "請先登入會員", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setView(view: View?) {
        //未登入
        rlLogout = view?.findViewById(R.id.rl_logout)
        etAcc = view?.findViewById(R.id.et_account)
        etPsw = view?.findViewById(R.id.et_password)
        cbLogin = view?.findViewById(R.id.cb_login)
        btLogin = view?.findViewById(R.id.bt_login)
        btRegister = view?.findViewById(R.id.bt_register)

        btLogin?.setOnClickListener {
            checkLogin()
        }
        btRegister?.setOnClickListener {
            val intent = Intent()
            mOwnActivity?.let { it1 ->
                intent.setClass(it1, RegisterActivity::class.java)
                it1.startActivity(intent)
            }
        }

        //已登入
        rlLogin = view?.findViewById(R.id.rl_login)
        tvUserName = view?.findViewById(R.id.tv_user_name)
        btLogout = view?.findViewById(R.id.bt_logout)
        rvAlsoLike = view?.findViewById(R.id.rv_also_like)

        btLogout?.setOnClickListener {
            setLogOut()
        }

        mAdapter = mOwnActivity?.let {it ->
            AlsoLikeAdapter(it, fragmentManager)
        }
        rvAlsoLike?.layoutManager = GridLayoutManager(mOwnActivity, 2)
        rvAlsoLike?.adapter = mAdapter
    }

    private fun getData() {
        showProgressDialog()
        val call = ApiClientBuilder.createApiClient().getAllData()
        call.enqueue(object : Callback<AllData> {
            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
                closeProgressDialog()
                mAllData = response.body()
                mAllData?.PromotionData?.let {
                    mAdapter?.setData(it)
                }
            }

            override fun onFailure(call: Call<AllData>, t: Throwable) {
                closeProgressDialog()
                Log.d("response", "${t.message}")
            }
        })
    }

    private fun checkLogin() {
        if (TextUtils.isEmpty(etAcc?.text) || TextUtils.isEmpty(etPsw?.text)) {
            Toast.makeText(mOwnActivity, "請輸入帳號密碼", Toast.LENGTH_SHORT).show()
        } else {
            val data = checkAcc()
            if (etPsw?.text.toString() == data?.psw) {
                if (cbLogin?.isChecked!!) {
                    Utility.saveStringValueForKey(mOwnActivity, Constants.REMEMBER_LOGIN, "true")
                } else {
                    Utility.saveStringValueForKey(mOwnActivity, Constants.REMEMBER_LOGIN, "false")
                }
                Utility.saveStringValueForKey(mOwnActivity, Constants.LOGIN_DATA, Utility.convertGsonToString(data))
                setLogin(data)
            } else {
                Toast.makeText(mOwnActivity, "密碼錯誤", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAcc(): MemberData? {
        var accData: MemberData? = null
        mAllData?.MemberData?.let {
            for (i in 0 until it.size) {
                if (it[i].acc == etAcc?.text.toString()) {
                    it[i].let { data ->
                        accData = data
                    }
                    break
                } else {
                    accData = null
                }
            }
        }
        return accData
    }

    private fun setLogin(data: MemberData) {
        rlLogout?.visibility = View.GONE
        rlLogin?.visibility = View.VISIBLE
        tvUserName?.text = data.name

    }

    private fun setLogOut() {
        rlLogin?.visibility = View.GONE
        rlLogout?.visibility = View.VISIBLE
        Utility.saveStringValueForKey(mOwnActivity, Constants.REMEMBER_LOGIN, "false")
        Utility.saveStringValueForKey(mOwnActivity, Constants.LOGIN_DATA, null)
    }
}