package com.software.buy3c.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.reflect.TypeToken
import com.software.buy3c.MyApplication
import com.software.buy3c.R
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.util.Constants
import com.software.buy3c.util.Utility

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
    var ref: DatabaseReference? = null

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

    fun removeItemData(position: Int) {
        val dataString = Utility.getStringValueForKey(context, Constants.LOGIN_DATA)
        val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("MemberData")
        for (i in 0 until MyApplication.mAllData?.MemberData?.size!!) {
            if (resultObj?.acc == MyApplication.mAllData?.MemberData?.get(i)?.acc) {
                MyApplication.mAllData?.MemberData!![i].proData?.removeAt(position)
                resultObj?.proData?.removeAt(position)
                Utility.saveStringValueForKey(context, Constants.LOGIN_DATA, Utility.convertGsonToString(resultObj))
                break
            }
        }
        ref?.setValue(MyApplication.mAllData?.MemberData)
        mData.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        private val ivShopping = v.findViewById<ImageView>(R.id.iv_shopping_car)
        private val tvShoppingName = v.findViewById<TextView>(R.id.tv_shopping_name)
        private val tvShoppingPrice = v.findViewById<TextView>(R.id.tv_shopping_price)
        private val ivCancel = v.findViewById<ImageView>(R.id.iv_cancel)

        fun bind(position: Int, mData: ArrayList<ProdData>) {
            val data = mData[position]

            Glide.with(context)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivShopping)

            tvShoppingName.text = data.name
            tvShoppingPrice.text = data.discount.toString()

            ivCancel.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.app_name)
                builder.setMessage("確定取消商品嗎?")
                builder.setPositiveButton("確定",
                    DialogInterface.OnClickListener { _, _ ->
                        removeItemData(position)
                    })
                builder.setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
                val alert: AlertDialog = builder.create()
                alert.show()
            }

        }
    }
}