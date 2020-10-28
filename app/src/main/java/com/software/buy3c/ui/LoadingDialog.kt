package com.software.buy3c.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.software.buy3c.R

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/27.
 * #主維護
 * #副維護
 */
class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(R.layout.dig_progress)
    }
}