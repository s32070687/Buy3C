package com.example.buy3c

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.buy3c.fragment.HomeFragment
import com.example.buy3c.fragment.HotCampingFragment
import com.example.buy3c.fragment.HotSaleFragment
import com.example.buy3c.fragment.MemberFragment
import com.example.buy3c.ui.adapter.MainViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.software.buy3c.R


class MainActivity : AppCompatActivity() {

//    private var mToolbar: Toolbar? = null
    private var mViewPage: ViewPager? = null
    private var mTabLayout: TabLayout? = null
    private var mPagerAdapter: MainViewPagerAdapter? = null
    var tabName = arrayOf("首頁", "熱銷榜", "熱門活動", "會員中心")
    var tabIcon = intArrayOf(
        R.drawable.home_tab_selector,
        R.drawable.hot_sale_tab_selector,
        R.drawable.hot_activity_tab_selector,
        R.drawable.member_tab_selector
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
//        mToolbar = findViewById(R.id.tb_main)
        mViewPage = findViewById(R.id.vp_main)
        mTabLayout = findViewById(R.id.tl_main)

        val fragments: ArrayList<Fragment> = ArrayList()
        fragments.add(HomeFragment())
        fragments.add(HotSaleFragment())
        fragments.add(HotCampingFragment())
        fragments.add(MemberFragment())

        mPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        mPagerAdapter?.setFragments(fragments)

        mViewPage?.adapter = mPagerAdapter
        mTabLayout?.setupWithViewPager(mViewPage)
        setTabItem()

    }

    private fun setTabItem() {
        for (i in 0..3) {
            val view: View = LayoutInflater.from(this).inflate(R.layout.tab_item, null)
            val tvTabName: TextView = view.findViewById(R.id.tab_name) as TextView
            tvTabName.text = tabName[i]
            val imageView: ImageView = view.findViewById(R.id.tab_img) as ImageView
            imageView.setImageResource(tabIcon[i])
            mTabLayout?.getTabAt(i)?.customView = view
        }
    }
}