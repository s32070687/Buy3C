package com.software.buy3c.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

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
object BackHandlerHelper {
    /**
     * 將back事件分發给 FragmentManager 中管理的子Fragment，如果該 FragmentManager 中的所有Fragment都
     * 沒有處理back事件，則嘗試 FragmentManager.popBackStack()
     *
     * @return 如果處理了back鍵則返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     */
    private fun handleBackPress(fragmentManager: FragmentManager?): Boolean {
        val fragments = fragmentManager?.fragments ?: return false

        for (i in fragments.indices.reversed()) {
            val child = fragments[i]

            if (isFragmentBackHandled(
                    child
                )
            ) {
                return true
            }
        }

        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    fun handleBackPress(fragment: Fragment?): Boolean {
        return handleBackPress(fragment?.childFragmentManager)
    }

    fun handleBackPress(fragmentActivity: FragmentActivity?): Boolean {
        return handleBackPress(
            fragmentActivity?.supportFragmentManager
        )
    }

    /**
     * 判斷Fragment是否處理了Back鍵
     *
     * @return 如果處理了back鍵則返回 **true**
     */
    private fun isFragmentBackHandled(fragment: Fragment?): Boolean {
        return (fragment != null
                && fragment.isVisible
                && fragment.userVisibleHint //for ViewPager
                && fragment is FragmentBackHandler
                && (fragment as FragmentBackHandler).onBackPressed())
    }
}