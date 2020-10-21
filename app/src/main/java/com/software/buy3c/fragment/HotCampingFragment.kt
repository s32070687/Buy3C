package com.software.buy3c.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import com.google.firebase.database.*
import com.software.buy3c.ui.BaseFragment
import com.software.buy3c.ui.FragmentPageManager
import com.software.buy3c.R
import com.software.buy3c.api.gson.CampingData

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
class HotCampingFragment : BaseFragment() {

    private var bt: Button? = null
    private var ref: DatabaseReference? = null
    private var mData: ArrayList<CampingData>? = null

    @Suppress("PrivatePropertyName")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hotcamping_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mFpm = FragmentPageManager(mOwnActivity, fragmentManager)
        mData = ArrayList<CampingData>()
        ref = FirebaseDatabase.getInstance().reference.child("HomeData").child("CampingData")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e("Jason","${dataSnapshot.value} value")
                mData = dataSnapshot.value as ArrayList<CampingData>?
                Log.e("Jason","${mData?.size} size")
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        ref?.addValueEventListener(postListener)
        setView(view)

    }

    private fun setView(view: View) {
        bt = view.findViewById(R.id.bt)
        bt?.setOnClickListener {
            ref?.child("CampingData")?.setValue(setData())
            Log.e("Jason","insert")
        }
    }

    private fun setData(): ArrayList<CampingData> {

        val data1 = CampingData()
        data1.Name = "凡購買ROG PHONE3系列手機送ROG炫光保護殼+ROG桌上型遊戲基座】ROG Phone 3 (ZS661KS  12G/512G)"
        data1.Id = 1
        data1.ImageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602724405_201408A1800001.jpg"
        data1.Price = 29990
        data1.Discount = 25000

        val data2 = CampingData()
        data2.Name = "Zenfone 7 Pro 十月好禮 翻轉鉅獻 登錄送 1MOEW Stylish 真無線藍牙耳機 "
        data2.Id = 2
        data2.ImageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1601460612_201408A1800001.jpg"
        data2.Price = 18000
        data2.Discount = 15000

        val data3 = CampingData()
        data3.Name = "Zenfone 7 Pro 十月好禮 翻轉鉅獻 登錄送三軸穩定器】ZenFone 7 Pro (ZS671KS 8G/256G)"
        data3.Id = 3
        data3.ImageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602464987_201408A1800001.jpg"
        data3.Price = 16000
        data3.Discount = 13000

        val data4 = CampingData()
        data4.Name = "ASUS Store 讀賣機種 "
        data4.Id = 4
        data4.ImageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602667934_201408A1800001.jpg"
        data4.Price = 20000
        data4.Discount = 18000

        val data5 = CampingData()
        data5.Name = "戰場遊我罩ROG周邊58折起"
        data5.Id = 5
        data5.ImageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1601264969_201408A1800001.jpg"
        data5.Price = 15000
        data5.Discount = 7500

        mData?.add(data1)
        mData?.add(data2)
        mData?.add(data3)
        mData?.add(data4)
        mData?.add(data5)

        return mData!!
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
            (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.hot_camping_title)
            mOwnActivity?.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))

            val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
            btnActionBack.visibility = View.INVISIBLE

            val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
            btnActionCar.visibility = View.VISIBLE
            btnActionCar.setOnClickListener {
                Log.e("Jason","HotCamping 購物車 ")
            }
        }
    }
}