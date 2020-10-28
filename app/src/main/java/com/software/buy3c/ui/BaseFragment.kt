package com.software.buy3c.ui

import android.content.Context
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.software.buy3c.MyApplication

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
@Suppress("PropertyName")
open class BaseFragment : Fragment(), FragmentBackHandler {

    protected var mOwnActivity: AppCompatActivity? = null
    protected var mRes: Resources? = null
    protected var mFpm: FragmentPageManager? = null

    private var mToolBar: ActionBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOwnActivity = activity as? AppCompatActivity
        mOwnActivity?.let {
            mRes = it.resources
            mToolBar = it.supportActionBar
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed(): Boolean {
        return BackHandlerHelper.handleBackPress(this) //預設為return true，FragmentManager會自動popupBack();
    }

    // Jun CR #6778
    override fun onPause() {

        mOwnActivity?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = it.currentFocus
            if (view != null && imm != null) {
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        super.onPause()
    }

    protected fun showProgressDialog() {
        mOwnActivity?.let {
            if (!it.isFinishing) {
                (it.application as MyApplication).showProgressDialog(it)
            }
        }
    }

    protected fun closeProgressDialog() {
        mOwnActivity?.let {
            if (!it.isFinishing) {
                (it.application as MyApplication).closeProgressDialog()
            }
        }
    }

    protected fun isSafeFragment(frag: Fragment): Boolean {
        return !(frag.isRemoving || frag.activity == null || frag.isDetached || !frag.isAdded || frag.view == null)
    }
}