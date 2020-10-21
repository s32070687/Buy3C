package com.software.buy3c.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
class FragmentPageManager(private val mCtx: Context?, private val mFm: FragmentManager?) {
    var curDisplayFragment: Fragment? = null  // 取得當前正在顯示的Fragment
        private set
    private var isOnlyPage = true
    private var animationType = 1
    var isFirstCreate = true
        private set
    val stackEntryCount:Int
        get() = mFm?.backStackEntryCount ?: 0

    /**
     * 取得當前Fragment下的 ChildFragmentManager
     * @return
     */
    val curChildFragmentManager: FragmentManager?
        get() = if (curDisplayFragment != null && !curDisplayFragment!!.isAdded) null
        else curDisplayFragment?.childFragmentManager

    val fragmentManager: FragmentManager? = mFm

    /**
     * 堆疊Fragment頁面的方法，<br></br>
     * 除了Tab容器使用項目外得其他Fragment皆使用這個方法<br></br>
     * 在使用前若加入之Fragment需重複使用（例如 商品資訊頁之Fragment，或商品列表頁之Fragment）<br></br>
     * 請call [FragmentPageManager.setOnlyPage],並傳false即可
     *
     * @param containerResId 放置Fragment容器之元件id
     * @param fragmentClass  需呈現之Fragment
     * @param bundleData     傳入參數
     * @param isAddedStack   是否需要加入堆疊中
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Throws(IllegalAccessException::class, InstantiationException::class)
    fun replace(containerResId:Int, fragmentClass:Class<out Fragment>, bundleData: Bundle?, isAddedStack:Boolean) {
        //查看Fragment是否存在，不存在才用Replace已存在時則指定當前存在之Fragment，防止GC時會多一頁
        if (mFm?.findFragmentByTag(fragmentClass.toString()) != null) {
            curDisplayFragment = mFm.findFragmentByTag(fragmentClass.toString())
        } else {
            curDisplayFragment = fragmentClass.newInstance()
            if (curDisplayFragment != null && bundleData != null) curDisplayFragment!!.arguments = bundleData

//            val ft = this.mFm?.beginTransaction()
            val fragmentTag =
                getFragmentTag(
                    fragmentClass,
                    isOnlyPage
                )
            mFm?.let {
                val ft = it.beginTransaction()
                when (animationType) {
                    ANIM_TYPE_SHOPPING_PART -> ft.setCustomAnimations(R.anim.fragment_slide_bottom_in, 0) // 由下而上
                    ANIM_TYPE_NORMAL_SHOW -> ft.setCustomAnimations(R.anim.fragment_slide_left_in, 0, 0, R.anim.fragment_slide_left_out) // 由右至左
                    ANIM_TYPE_SHOW_FROM_TAB -> {} //沒動作
                }
                curDisplayFragment?.let { current ->
                    ft.replace(containerResId, current, fragmentTag)
                }
//                ft.replace(containerResId, curDisplayFragment, fragmentTag)

                if (isAddedStack) ft.addToBackStack(fragmentTag)
                ft.commitAllowingStateLoss()
            }
        }
    }

    fun popupStack() {
        mFm?.popBackStack()
    }
    /**
     * 設定是否為不重複頁面（同一個Fragment在一個流程中只會出現一次）是則設true
     * 預設為true
     * @param onlyPage
     */
    fun setOnlyPage(onlyPage: Boolean) {
        isOnlyPage = onlyPage
    }

    companion object {
        var count = 0
        const val ANIM_TYPE_SHOPPING_PART = 0
        const val ANIM_TYPE_NORMAL_SHOW = 1
        const val ANIM_TYPE_SHOW_FROM_TAB = 2

        fun getFragmentTag(fragmentClass:Class<out Fragment>, isOnlyPage:Boolean):String {
            return if(isOnlyPage) fragmentClass.toString()
            else fragmentClass.toString() + count++.toString()
        }
    }
}