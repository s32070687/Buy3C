package com.example.buy3c

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import com.software.buy3c.R

class MainActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null
    private var mTabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        mToolbar = findViewById(R.id.tb_main)
        mTabLayout = findViewById(R.id.tl_main)

        mToolbar?.let {
            it.inflateMenu(R.menu.menu)
            it.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_shopping_cart -> {
                        Log.e("Jason","購物車")
                    }
                }
                false
            }
        }

        mTabLayout?.let {
            mTabLayout?.addTab(it.newTab().setText(R.string.home_title).setIcon(R.drawable.home_tab_selector))
            mTabLayout?.addTab(it.newTab().setText(R.string.hot_sale_title).setIcon(R.drawable.hot_sale_tab_selector))
            mTabLayout?.addTab(it.newTab().setText(R.string.hot_camping_title).setIcon(R.drawable.hot_activity_tab_selector))
            mTabLayout?.addTab(it.newTab().setText(R.string.member_title).setIcon(R.drawable.member_tab_selector))
        }

        mTabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

    private fun tabSelect(position: Int?) {
        when(position) {
            0 -> {

            }
            1 -> {

            }
            2 -> {

            }
            3 -> {

            }
        }
    }
}