package com.software.buy3c.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.software.buy3c.R
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.ui.FragmentPageManager
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
class HotSaleAdapter (private val context: Context, mFm: FragmentManager?, val mFpm : FragmentPageManager = FragmentPageManager(context,mFm)) : RecyclerView.Adapter<HotSaleAdapter.ViewHolder>() {

    var mData = ArrayList<ProdData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotsale_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position,mData)
    }

    fun setData(data: ArrayList<ProdData>) {
        mData = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        private val rlProdItem = v.findViewById<RelativeLayout>(R.id.rl_hot_sale)
        private val ivHotSale = v.findViewById<ImageView>(R.id.iv_hot_sale)
        private val tvHotSaleName = v.findViewById<TextView>(R.id.tv_hot_sale_name)
        private val tvHotSalePrice = v.findViewById<TextView?>(R.id.tv_hot_sale_price)

        @SuppressLint("SetTextI18n")
        @Suppress("DEPRECATION")
        fun bind(position: Int, mData: ArrayList<ProdData>) {
            val data = mData[position]

            Glide.with(context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivHotSale)

            tvHotSaleName?.text = data.name
            tvHotSalePrice?.text = "$ ${data.discount.toString()}"

            rlProdItem.setOnClickListener {
                val intent = Intent()
                intent.setClass(context, ProdItemActivity::class.java)
                intent.putExtra("prodData", data)
                context.startActivity(intent)
            }
        }
    }
}