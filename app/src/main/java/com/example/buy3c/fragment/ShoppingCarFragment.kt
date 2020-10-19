package com.example.buy3c.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.buy3c.ui.BaseFragment
import com.software.buy3c.R

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by laijisheng on 2020/10/19.
 * #主維護
 * #副維護
 */
class ShoppingCarFragment : BaseFragment() {
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
            (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.shopping_car_title)
            mOwnActivity?.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))

            val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
            btnActionBack.setOnClickListener { mOwnActivity?.onBackPressed() }

            val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
            btnActionCar.visibility = View.GONE
        }
    }
}