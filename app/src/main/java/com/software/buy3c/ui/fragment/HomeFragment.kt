package com.software.buy3c.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.software.buy3c.R
import com.software.buy3c.api.ApiClientBuilder
import com.software.buy3c.api.gson.AllData
import com.software.buy3c.ui.BaseFragment
import com.software.buy3c.ui.FragmentPageManager
import com.software.buy3c.ui.adapter.HomeAdapter
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

@Suppress("DEPRECATION")
class HomeFragment : BaseFragment() {

    private var rvHome: RecyclerView? = null
    private var mAllData: AllData? = null

    @Suppress("PrivatePropertyName")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mFpm = FragmentPageManager(mOwnActivity, fragmentManager)
        setView(view)
        getData()
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
            (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.home_title)
            mOwnActivity?.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))

            val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
            btnActionBack.visibility = View.INVISIBLE

            val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
            btnActionCar.visibility = View.VISIBLE
            btnActionCar.setOnClickListener {
                Log.e("Jason","Home 購物車 ")
            }
        }
    }

    private fun setView(view: View?) {
        rvHome = view?.findViewById(R.id.rv_home)
        rvHome?.layoutManager = LinearLayoutManager(mOwnActivity)
    }

    private fun getData() {
        val call = ApiClientBuilder.createApiClient().getAllData()
        call.enqueue(object : Callback<AllData> {

            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
                mAllData = response.body()
                val adapter = mOwnActivity?.let {
                    mAllData?.HomeData?.let { data -> HomeAdapter(it, data, fragmentManager) }
                 }
                rvHome?.adapter = adapter
            }

            override fun onFailure(call: Call<AllData>, t: Throwable) {
                Log.d("response", "${t.message}")
            }
        })
    }
}