package com.software.buy3c.ui.adapter

import android.content.Context
import android.content.Intent
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
 * Created by Jason on 2020/10/27.
 * #主維護
 * #副維護
 */

class AlsoLikeAdapter (private val context: Context, mFm: FragmentManager?, val mFpm : FragmentPageManager = FragmentPageManager(context,mFm)) : RecyclerView.Adapter<AlsoLikeAdapter.ViewHolder>() {

    var mData = ArrayList<ProdData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlsoLikeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_prod_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: AlsoLikeAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(data: ArrayList<ProdData>) {
        mData = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        private val rlProdItem = v.findViewById<RelativeLayout>(R.id.rl_prod_item)
        private val ivProd = v.findViewById<ImageView>(R.id.iv_prod)
        private val tvProdName = v.findViewById<TextView>(R.id.tv_prod_name)
        private val tvProdPrice = v.findViewById<TextView?>(R.id.tv_price)

        fun bind(position: Int) {
            val data = mData[position]
            Glide.with(context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivProd)

            rlProdItem.setOnClickListener {
                val intent = Intent()
                intent.setClass(context, ProdItemActivity::class.java)
                intent.putExtra("prodData", data)
                context.startActivity(intent)
            }
            tvProdName?.text = data.name
            tvProdPrice?.text = data.discount.toString()

        }
    }
}