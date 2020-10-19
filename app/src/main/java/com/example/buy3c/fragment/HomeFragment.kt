package com.example.buy3c.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import com.example.buy3c.ui.BaseFragment
import com.example.buy3c.ui.FragmentPageManager
import com.software.buy3c.R

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

class HomeFragment : BaseFragment() {

    @Suppress("PrivatePropertyName")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("Jason","Home")
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mFpm = FragmentPageManager(mOwnActivity, fragmentManager)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.e("Jason","onCreateOptionsMenu")
        menu.clear()
        setActionBar()
    }

    @SuppressLint("InflateParams")
    private fun setActionBar() {
        Log.e("Jason","setActionBar")
        if (mOwnActivity != null) {
            Log.e("Jason","${mOwnActivity?.supportActionBar} supportActionBar")
            mOwnActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowHomeEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowCustomEnabled(true)
            val mInflater = LayoutInflater.from(mOwnActivity)
            val actionbar = mInflater.inflate(R.layout.actionbar_main, null)
            (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.home_title)
            mOwnActivity?.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))

            val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
            btnActionBack.setOnClickListener { mOwnActivity?.onBackPressed() }

            val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
            btnActionCar.visibility = View.VISIBLE
            btnActionBack.setOnClickListener {
                Log.e("Jason","Home 購物車 ")
            }
        }
    }
}