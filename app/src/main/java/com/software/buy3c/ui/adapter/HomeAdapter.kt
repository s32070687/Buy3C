package com.software.buy3c.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.software.buy3c.ui.AutoHeightViewPager
import com.software.buy3c.ui.FragmentPageManager
import com.google.android.material.tabs.TabLayout
import com.software.buy3c.R
import com.software.buy3c.api.gson.CampingData
import com.software.buy3c.api.gson.HomeData

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

class HomeAdapter (private val context: Context, val mData: HomeData, mFm: FragmentManager?, val mFpm : FragmentPageManager = FragmentPageManager(context,mFm)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val minScale = 0.80f
    private val center = 0.5f

    @Suppress("ClassName")
    enum class ITEM_TYPE {
        ITEM_TYPE_BANNER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE.ITEM_TYPE_BANNER.ordinal -> ViewHolder_Banner(mInflater.inflate(R.layout.home_banner, parent, false))
            else -> ViewHolder_Prod(mInflater.inflate(R.layout.home_prod, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == ITEM_TYPE.ITEM_TYPE_BANNER.ordinal) {
            val holderBanner = holder as HomeAdapter.ViewHolder_Banner
            val bannerAdapter = BannerAdapter(context)
            holderBanner.vgBanner.offscreenPageLimit = 3
            holderBanner.vgBanner.pageMargin = 30
            // add 滑動動畫
            holderBanner.vgBanner.setPageTransformer(false,ScaleInTransformer())
            holderBanner.vgBanner.adapter = bannerAdapter
            holderBanner.tabLayout.setupWithViewPager(holderBanner.vgBanner, true)

        } else {
            val holderProd = holder as HomeAdapter.ViewHolder_Prod
            val prodAdapter = ProdItemAdapter(context)
            holderProd.rvProd.layoutManager = GridLayoutManager(context, 2)
            holderProd.rvProd.adapter = prodAdapter
        }
    }

    @Suppress("ClassName")
    internal inner class ViewHolder_Banner(
        itemView: View,
        val vgBanner: AutoHeightViewPager = (itemView.findViewById<View>(R.id.vg_banner_container) as AutoHeightViewPager),
        val tabLayout: TabLayout = (itemView.findViewById<View>(R.id.tab_layout) as TabLayout)
    ) : RecyclerView.ViewHolder(itemView)

    @Suppress("ClassName")
    internal inner class ViewHolder_Prod(
        itemView: View,
        val rvProd: RecyclerView = (itemView.findViewById<View>(R.id.rv_prod) as RecyclerView)
    ) : RecyclerView.ViewHolder(itemView)

    inner class BannerAdapter(mContext: Context?) : PagerAdapter() {
        var mLayoutInflater: LayoutInflater = mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
            return mData.CampingData?.size!!
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val itemView = mLayoutInflater.inflate(R.layout.home_banner_item, container, false)
            val ivBanner = itemView.findViewById<View>(R.id.iv_banner) as ImageView

            Glide.with(context)
                .load(mData.CampingData?.get(position)?.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivBanner)

            container.addView(itemView)
            return itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as RelativeLayout)
        }
    }

    inner class ProdItemAdapter(mContext: Context?) : RecyclerView.Adapter<ProdItemAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.home_prod_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mData.ProdData?.size!!
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position)
        }

        inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
            private val ivProd = v.findViewById<ImageView>(R.id.iv_prod)
            private val tvProdName = v.findViewById<TextView>(R.id.tv_prod_name)
            private val tvProdPrice = v.findViewById<TextView?>(R.id.tv_price)

            fun bind(position: Int) {

            }
        }
    }

    //滑動動畫
    inner class ScaleInTransformer : ViewPager.PageTransformer {
        private val mMinScale = minScale
        override fun transformPage(view: View, position: Float) {
            view.elevation = -kotlin.math.abs(position)
            val pageWidth = view.width
            val pageHeight = view.height

            view.pivotY = (pageHeight / 2).toFloat()
            view.pivotX = (pageWidth / 2).toFloat()
            if (position < -1) {
                view.scaleX = mMinScale
                view.scaleY = mMinScale
                view.pivotX = pageWidth.toFloat()
            } else if (position <= 1) {
                if (position < 0) {
                    val scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale
                    view.scaleX = scaleFactor
                    view.scaleY = scaleFactor
                    view.pivotX = pageWidth * (center + center * -position)
                } else {
                    val scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale
                    view.scaleX = scaleFactor
                    view.scaleY = scaleFactor
                    view.pivotX = pageWidth * ((1 - position) * center)
                }
            } else {
                view.pivotX = 0f
                view.scaleX = mMinScale
                view.scaleY = mMinScale
            }
        }
    }
}