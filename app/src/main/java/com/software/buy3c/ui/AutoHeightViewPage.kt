package com.software.buy3c.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/20.
 * #主維護
 * #副維護
 */
class AutoHeightViewPager : ViewPager {
    companion object {
//        val TAG = AutoHeightViewPager::class.java.simpleName
    }

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
//        Log.i(TAG, "onMeasure: getChildCount():$childCount")

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) {
                height = h
            }
        }

        val iHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, iHeightMeasureSpec)
    }
}