package com.software.buy3c.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.software.buy3c.R
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.ui.FragmentPageManager
import com.software.buy3c.ui.activity.CampingActivity
import com.software.buy3c.ui.activity.ProdItemActivity

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/26.
 * #主維護
 * #副維護
 */
class HotCampingAdapter(private val context: Context, mFm: FragmentManager?, val mFpm : FragmentPageManager = FragmentPageManager(context,mFm)) : RecyclerView.Adapter<HotCampingAdapter.ViewHolder>() {

    var mData = ArrayList<ProdData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotCampingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotcamping_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: HotCampingAdapter.ViewHolder, position: Int) {
        holder.bind(position,mData)
    }

    fun setData(data: ArrayList<ProdData>) {
        mData = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        private val clCampingItem = v.findViewById<ConstraintLayout>(R.id.cl_camping_item)
        private val ivCamping = v.findViewById<ImageView>(R.id.iv_camping)

        @SuppressLint("SetTextI18n")
        @Suppress("DEPRECATION")
        fun bind(position: Int, mData: ArrayList<ProdData>) {
            val data = mData[position]

            Glide.with(context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivCamping)

            clCampingItem.setOnClickListener {
                val intent = Intent()
                intent.setClass(context, CampingActivity::class.java)
                intent.putExtra("campingData", data)
                context.startActivity(intent)
            }
        }
    }
}