package com.software.buy3c.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.gson.reflect.TypeToken
import com.software.buy3c.MyApplication
import com.software.buy3c.ui.BaseFragment
import com.software.buy3c.ui.FragmentPageManager
import com.software.buy3c.R
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.ui.activity.ShoppingCarActivity
import com.software.buy3c.ui.adapter.HotSaleAdapter
import com.software.buy3c.util.Constants
import com.software.buy3c.util.Utility

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
class HotSaleFragment : BaseFragment() {

    private var tlMain: TabLayout? = null
    private var rvHotSale: RecyclerView? = null
    private var mAdapter: HotSaleAdapter? = null
    private var mData0: ArrayList<ProdData>? = null
    private var mData1: ArrayList<ProdData>? = null
    private var mData2: ArrayList<ProdData>? = null
    private var mData3: ArrayList<ProdData>? = null

    @Suppress("PrivatePropertyName")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hotsale_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mFpm = FragmentPageManager(mOwnActivity, fragmentManager)
        setView(view)
        getData()
        setTabItem()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        setActionBar()
    }

    @SuppressLint("InflateParams")
    private fun setActionBar() {
        if (mOwnActivity != null) {
            mOwnActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowHomeEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowCustomEnabled(true)
            val mInflater = LayoutInflater.from(mOwnActivity)
            val actionbar = mInflater.inflate(R.layout.actionbar_main, null)
            (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.hot_sale_title)
            mOwnActivity?.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))

            val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
            btnActionBack.visibility = View.INVISIBLE

            val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
            btnActionCar.visibility = View.VISIBLE
            btnActionCar.setOnClickListener {
                val intent = Intent()
                mOwnActivity?.let { it1 ->
                    val dataString = Utility.getStringValueForKey(it1, Constants.LOGIN_DATA)
                    val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
                    if (resultObj != null) {
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
        tlMain = view?.findViewById(R.id.tl_hot_sale_main)
        rvHotSale = view?.findViewById(R.id.rv_hot_sale)

        rvHotSale?.layoutManager = LinearLayoutManager(mOwnActivity)
        mAdapter = mOwnActivity?.let {it ->
            HotSaleAdapter(it, fragmentManager)
        }
        rvHotSale?.adapter = mAdapter
    }

    private fun setTabItem() {
        tlMain?.let { it ->
            it.addTab(it.newTab().setText(R.string.hot_sale_tab_0),true)
            it.addTab(it.newTab().setText(R.string.hot_sale_tab_1))
            it.addTab(it.newTab().setText(R.string.hot_sale_tab_4))
            it.addTab(it.newTab().setText(R.string.hot_sale_tab_3))

            it.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabSelect(tab?.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    tabSelect(tab?.position)
                }
            })
        }
    }

    private fun tabSelect(position: Int?) {
        when(position) {
            0 -> {
                mData0?.let { it1 -> mAdapter?.setData(it1) }
            }
            1 -> {
                mData1?.let { it1 -> mAdapter?.setData(it1) }
            }
            2 -> {
                mData2?.let { it1 -> mAdapter?.setData(it1) }
            }
            3 -> {
                mData3?.let { it1 -> mAdapter?.setData(it1) }
            }
        }
    }

    private fun getData() {
        mData0 = ArrayList<ProdData>()
        mData1 = ArrayList<ProdData>()
        mData2 = ArrayList<ProdData>()
        mData3 = ArrayList<ProdData>()
        MyApplication.mAllData?.HotSaleData?.let { data ->
            for (i in 0 until data.size) {
                when(data[i].type){
                    0 -> {
                        mData0?.add(data[i])
                    }
                    1 -> {
                        mData1?.add(data[i])
                    }
                    3 -> {
                        mData3?.add(data[i])
                    }
                    4 -> {
                        mData2?.add(data[i])
                    }
                }
            }
            mData0?.let { it -> mAdapter?.setData(it) }
        }
    }
}