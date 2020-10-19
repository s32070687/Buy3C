package com.example.buy3c.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter




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
@Suppress("DEPRECATION")
class MainViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    private var mFragmentList: List<Fragment>? = null
    fun setFragments(fragments: ArrayList<Fragment>?) {
        mFragmentList = fragments
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList!![position]
    }

    override fun getCount(): Int {
        return mFragmentList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}