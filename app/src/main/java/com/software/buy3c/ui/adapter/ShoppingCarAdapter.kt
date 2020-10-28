package com.software.buy3c.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
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
 * Created by Jason on 2020/10/28.
 * #主維護
 * #副維護
 */
class ShoppingCarAdapter (private val context: Context) : RecyclerView.Adapter<ShoppingCarAdapter.ViewHolder>() {

    var mData = ArrayList<ProdData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCarAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_car_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ShoppingCarAdapter.ViewHolder, position: Int) {
        holder.bind(position,mData)
    }

    fun setData(data: ArrayList<ProdData>) {
        mData = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        private val rlShoppingItem = v.findViewById<RelativeLayout>(R.id.rl_shopping_item)
        private val ivShopping = v.findViewById<ImageView>(R.id.iv_shopping_car)
        private val tvShoppingName = v.findViewById<TextView>(R.id.tv_shopping_name)
        private val tvShoppingPrice = v.findViewById<TextView>(R.id.tv_shopping_price)

        fun bind(position: Int, mData: ArrayList<ProdData>) {
            val data = mData[position]

            Glide.with(context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivShopping)

            tvShoppingName.text = data.name
            tvShoppingPrice.text = data.discount.toString()

            rlShoppingItem.setOnClickListener {
                val intent = Intent()
                intent.setClass(context, ProdItemActivity::class.java)
                intent.putExtra("prodData", data)
                context.startActivity(intent)
            }
        }
    }
}