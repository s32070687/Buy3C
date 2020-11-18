package com.software.buy3c.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.gson.reflect.TypeToken
import com.software.buy3c.MyApplication
import com.software.buy3c.ui.BaseFragment
import com.software.buy3c.ui.FragmentPageManager
import com.software.buy3c.R
import com.software.buy3c.api.ApiClientBuilder
import com.software.buy3c.api.gson.AllData
import com.software.buy3c.api.gson.MemberData
import com.software.buy3c.api.gson.ProdData
import com.software.buy3c.ui.activity.ShoppingCarActivity
import com.software.buy3c.ui.adapter.HotCampingAdapter
import com.software.buy3c.ui.adapter.HotSaleAdapter
import com.software.buy3c.util.Constants
import com.software.buy3c.util.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
class HotCampingFragment : BaseFragment() {

    private var bt: Button? = null
    private var rvHotCamping: RecyclerView? = null
    private var mAdapter: HotCampingAdapter? = null
    private var ref: DatabaseReference? = null
    private var mData: ArrayList<ProdData>? = null
    private var mData1: ArrayList<ProdData>? = null

    @Suppress("PrivatePropertyName")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hotcamping_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
//        setDataBase()
        mFpm = FragmentPageManager(mOwnActivity, fragmentManager)
        setView(view)
        getData()
    }

    private fun setView(view: View) {
        rvHotCamping = view.findViewById(R.id.rv_hot_camping)
        mAdapter = mOwnActivity?.let {it ->
            HotCampingAdapter(it, fragmentManager)
        }
        rvHotCamping?.layoutManager = LinearLayoutManager(mOwnActivity)
        rvHotCamping?.adapter = mAdapter
        bt = view.findViewById(R.id.bt)
        bt?.setOnClickListener {
//            ref?.setValue(setData5())
        }
    }

    private fun getData() {
        MyApplication.mAllData?.HotCampingData?.let {data ->
            mAdapter?.setData(data)
        }
//        showProgressDialog()
//        val call = ApiClientBuilder.createApiClient().getAllData()
//        call.enqueue(object : Callback<AllData> {
//
//            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
//                closeProgressDialog()
//                val data = response.body()
//                data?.HotCampingData?.let { mAdapter?.setData(it) }
//            }
//
//            override fun onFailure(call: Call<AllData>, t: Throwable) {
//                closeProgressDialog()
//                Log.d("response", "${t.message}")
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        setActionBar()
    }

    @SuppressLint("InflateParams")
    private fun setActionBar() {
        if (mOwnActivity != null) {
            mOwnActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowHomeEnabled(false)
            mOwnActivity?.supportActionBar?.setDisplayShowCustomEnabled(true)
            val mInflater = LayoutInflater.from(mOwnActivity)
            val actionbar = mInflater.inflate(R.layout.actionbar_main, null)
            (actionbar.findViewById<View>(R.id.tv_title) as TextView).text = getString(R.string.hot_camping_title)
            mOwnActivity?.supportActionBar?.setCustomView(actionbar, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))

            val btnActionBack = actionbar.findViewById<ImageView>(R.id.iv_back)
            btnActionBack.visibility = View.INVISIBLE

            val btnActionCar = actionbar.findViewById<ImageView>(R.id.iv_shopping_car)
            btnActionCar.visibility = View.VISIBLE
            btnActionCar.setOnClickListener {
                val intent = Intent()
                mOwnActivity?.let { it1 ->
                    val dataString = Utility.getStringValueForKey(it1, Constants.LOGIN_DATA)
                    val resultObj = Utility.convertStringToGsonObj(dataString, object : TypeToken<MemberData>() {}.type) as MemberData?
                    if (resultObj != null) {
                        intent.setClass(it1, ShoppingCarActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(it1, "請先登入會員", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setDataBase() {
        mData = ArrayList<ProdData>()
        mData1 = ArrayList<ProdData>()
//        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("HomeData").child("CampingData")
//        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("HomeData").child("ProdData")
//        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("HotSaleData")
//        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("HotCampingData")
//        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("PromotionData")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        ref?.addValueEventListener(postListener)
    }

    //CampingData
    private fun setData(): ArrayList<ProdData> {

        val data1 = ProdData()
        data1.name = "凡購買ROG PHONE3系列手機送ROG炫光保護殼+ROG桌上型遊戲基座】ROG Phone 3 (ZS661KS  12G/512G)"
        data1.id = 1
        data1.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602724405_201408A1800001.jpg"
        data1.price = 29990
        data1.discount = 25000
        data1.detail = "S865Plus 5G 高通旗艦處理器\n" +
                "AMOLED 144Hz/1ms動態畫質\n" +
                "AirTrigger 3超音波觸控鍵\n" +
                "12G RAM / 512G ROM\n" +
                "6000mAh超大電量\n" +
                "全新X模式專家級系統效能調校\n" +
                "享延長保固6個月\n" +
                "隨貨送ROG炫光保護殼(價值\$1490)、限量加贈-ROG Phone 3 遊戲控制器 (價值\$3990)-限量售完即停"
        data1.type = 5

        val data2 = ProdData()
        data2.name = "Zenfone 7 Pro 十月好禮 翻轉鉅獻 登錄送 1MOEW Stylish 真無線藍牙耳機 "
        data2.id = 2
        data2.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1601460612_201408A1800001.jpg"
        data2.price = 18000
        data2.discount = 15000
        data2.detail = "於活動指定期間，購買ZenFone 7 (ZS670KS) (6G/128G & 8G/128G)，並於登錄期間，再到本活動專頁登錄資料(如尚未註冊成為ASUS官網會員者，應先完成註冊），符合資格者即可獲得「1MORE Stylish 真無線藍牙耳機」(市價\$2,790元，數量有限，送完為止) 乙個！"
        data2.type = 5

        val data3 = ProdData()
        data3.name = "Zenfone 7 Pro 十月好禮 翻轉鉅獻 登錄送三軸穩定器】ZenFone 7 Pro (ZS671KS 8G/256G)"
        data3.id = 3
        data3.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602464987_201408A1800001.jpg"
        data3.price = 16000
        data3.discount = 13000
        data3.detail = "6400 萬畫素 SONY 旗艦級 IMX686 感光元件\n" +
                "64 位元高通® Snapdragon™ 865 Plus 八核心 5G 處理器 ，搭載 7 奈米製程\n" +
                "6.67 吋 20:9 (2400 x 1080) AMOLED 螢幕，搭載 90 Hz 螢幕刷新率\n" +
                "ASUS Store及ZenFone專賣店 獨享延長保固6個月\n" +
                "產品金額超過2萬元超取上限，故不開放超商取貨，敬請見諒\n" +
                "9/1~10/31，指定通路買ZenFone 7 Pro (8G/256G) 登錄送 ASUS ZenGimbal 三軸穩定器！（建議售價\$4,990！限量，贈完為止）\n" +
                "煥彩白線商城已售完， 線上商城已售完不補，請點選門市取貨，挑選離您最近的專賣店下單，謝謝您"
        data3.type = 5

        val data4 = ProdData()
        data4.name = "ASUS Store 獨賣機種 "
        data4.id = 4
        data4.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1603674672_201408A1800001.jpg"
        data4.price = 20000
        data4.discount = 18000
        data4.detail = "ASUS Store 台灣 TW ROG 11月電競優惠, ROG Cetra RGB 入耳式電競耳機, 【贈電競鼠墊】ROG Strix Scope RX RGB 光學機械鍵盤 紅軸, 【贈電競鼠墊】ROG Strix Impact II Wireless 電競滑鼠"
        data4.type = 5

        val data5 = ProdData()
        data5.name = "戰場遊我罩ROG周邊58折起"
        data5.id = 5
        data5.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1601264969_201408A1800001.jpg"
        data5.price = 15000
        data5.discount = 7500
        data5.detail = "敗家了敗家了，如此邪惡的眼神看著你，打58折還不買爆它！華碩電競周邊十月來罩你啦！同時推出三樣新品- IMPACT II Wireless 無線終於出現了，二代小鋼炮已經備受好評，終於有無線款了！SCABBARD II 超大型滑鼠墊做了更棒奈米保護塗層的改版！還有一咖高達 22 公升的內部空間，最高可容納一台 17 吋筆電的 BP2701 電競背包！"
        data5.type = 0

        mData?.add(data1)
        mData?.add(data2)
        mData?.add(data3)
        mData?.add(data4)
        mData?.add(data5)

        return mData!!
    }

    //ProdDat
    private fun setData2(): ArrayList<ProdData> {

        //主機
        val data1 = ProdData()
        data1.name = "Creator P100X 10SE-252TW▼輕薄的品牌主機▼"
        data1.id = 1
        data1.imageUrl = "https://b.ecimg.tw/items/DSAA6IA900AUX1G/000001_1599467197.jpg"
        data1.price = 100000
        data1.discount = 99900
        data1.detail = "《 超狂特色》\n" +
                "★搭載最新第10代英特爾 酷睿i9-10900K處理器\n" +
                "★搭載MSI GeForce RTX 2080 SUPER 8GB GDDR6顯示卡\n" +
                "★採用DDR4 Boost技術的雙通道內存：提供最流暢，最快速的即時預覽\n" +
                "★內建Thunderbolt 3連接阜\n" +
                "★內建Wi-Fi6，速度高達2.4Gbps\n" +
                "★支援2x M.2 PCI-e SSD、2x2.5“SATA儲存裝置\n" +
                "★支援5K2K視覺體驗\n" +
                "★支援專業音質的音頻增強技術\n" +
                "★獨家軟體– Creator Center、Creator OSD\n" +
                "★輕薄的品牌主機，體積僅10.36升"
        data1.type = 0

        val data2 = ProdData()
        data2.name = "Creator P100A 10SI-256TW▼輕薄的品牌主機▼"
        data2.id = 2
        data2.imageUrl = "https://d.ecimg.tw/items/DSAA6IA900AUXLH/000001_1599473089.jpg"
        data2.price = 45000
        data2.discount = 43900
        data2.detail = "《 超狂特色》\n" +
                "★搭載最新第10代英特爾 酷睿i5-10400F處理器\n" +
                "★搭載MSI GeForce GTX 1660 SUPER 6GB GDDR6顯示卡\n" +
                "★採用DDR4 Boost技術的雙通道內存：提供最流暢，最快速的即時預覽\n" +
                "★內建Thunderbolt 3連接阜\n" +
                "★內建Wi-Fi6，速度高達2.4Gbps\n" +
                "★支援2x M.2 PCI-e SSD、2x2.5“SATA儲存裝置\n" +
                "★支援5K2K視覺體驗\n" +
                "★支援專業音質的音頻增強技術\n" +
                "★獨家軟體– Creator Center、Creator OSD\n" +
                "★輕薄的品牌主機，體積僅10.36升"
        data2.type = 0

        val data3 = ProdData()
        data3.name = "Creator P100X 10SD-253TW▼輕薄的品牌主機▼"
        data3.id = 3
        data3.imageUrl = "https://d.ecimg.tw/items/DSAA6IA900AUXD6/000001_1599470692.jpg"
        data3.price = 90000
        data3.discount = 89900
        data3.detail = "《 超狂特色》\n" +
                "★搭載最新第10代英特爾 酷睿i5-10400F處理器\n" +
                "★搭載MSI GeForce GTX 1660 SUPER 6GB GDDR6顯示卡\n" +
                "★採用DDR4 Boost技術的雙通道內存：提供最流暢，最快速的即時預覽\n" +
                "★內建Thunderbolt 3連接阜\n" +
                "★內建Wi-Fi6，速度高達2.4Gbps\n" +
                "★支援2x M.2 PCI-e SSD、2x2.5“SATA儲存裝置\n" +
                "★支援5K2K視覺體驗\n" +
                "★支援專業音質的音頻增強技術\n" +
                "★獨家軟體– Creator Center、Creator OSD\n" +
                "★輕薄的品牌主機，體積僅10.36升"
        data3.type = 0

        val data4 = ProdData()
        data4.name = "Creator P100A 10SI-257TW▼輕薄的品牌主機▼"
        data4.id = 4
        data4.imageUrl = "https://e.ecimg.tw/items/DSAA6IA900AV1ID/000001_1599616703.jpg"
        data4.price = 45000
        data4.discount = 43900
        data4.detail = "《 超狂特色》\n" +
                "★搭載最新第10代英特爾 酷睿i5-10400F處理器\n" +
                "★搭載MSI GeForce GTX 1660 SUPER 6GB GDDR6顯示卡\n" +
                "★採用DDR4 Boost技術的雙通道內存：提供最流暢，最快速的即時預覽\n" +
                "★內建Thunderbolt 3連接阜\n" +
                "★內建Wi-Fi6，速度高達2.4Gbps\n" +
                "★支援2x M.2 PCI-e SSD、2x2.5“SATA儲存裝置\n" +
                "★支援5K2K視覺體驗\n" +
                "★支援專業音質的音頻增強技術\n" +
                "★獨家軟體– Creator Center、Creator OSD\n" +
                "★輕薄的品牌主機，體積僅10.36升"
        data4.type = 0


        val data5 = ProdData()
        data5.name = "華碩 H-S340MCi5六核華碩電腦"
        data5.id = 5
        data5.imageUrl = "https://c.ecimg.tw/items/DSAADM1900AA2EZ/000001_1569478908.jpg"
        data5.price = 19000
        data5.discount = 18590
        data5.detail = "處理器：Intel Core i5-8400(2.8GHz)\n" +
                "記憶體：8G DDR4-2666MHz\n" +
                "硬碟：1TB\n" +
                "螢幕輸出介面：D-Sub(VGA)．DVI．HDMI\n" +
                "光碟機：DVD RW\n" +
                "無線網路：IEEE 802.11 ac + BT 4.1\n" +
                "其他：支援RS232 (COM Port)\n" +
                "鍵鼠組：有線鍵鼠組\n" +
                "作業系統：DOS (選購)\n" +
                "※本機器不含作業系統，請另購買\n" +
                "保固：三年保固/到府收送\n" +
                "(免費第一年到府維修)\n" +
                "(原廠於保固期內將自行或委託第三方到府收送)\n"
        data5.type = 0

        //螢幕
        val data6 = ProdData()
        data6.name = "BenQ 27型IPS玩色螢幕GW2780Plus"
        data6.id = 6
        data6.imageUrl = "https://e.ecimg.tw/items/DSABF2A9009WX9E/000001_1597022612.jpg"
        data6.price = 5100
        data6.discount = 4988
        data6.detail = "★內建玩色模式-大幅提升辨色力★\n" +
                "IPS 178度廣視角面板\n" +
                "1920x1080 FHD解析度\n" +
                "支援D-Sub/HDMI 1.4/DisplayPort介面\n" +
                "光智慧技術\n" +
                "德國萊茵低藍光、不閃屏雙認證\n" +
                "四段式低藍光模式\n" +
                "內建雙喇叭(2W)\n" +
                "輕薄全美型\n" +
                "可調整傾斜\n" +
                "支援壁掛功能\n" +
                "3年保固\n"
        data6.type = 1

        val data7 = ProdData()
        data7.name = "LG 22型AH-IPS電競螢幕 (22MP58VQ-P)"
        data7.id = 7
        data7.imageUrl = "https://e.ecimg.tw/items/DSAB07A9006V140/000001_1600136434.jpg"
        data7.price = 3000
        data7.discount = 2888
        data7.detail = "簡約時尚輕薄機身設計\n" +
                "電競模式：黑暗場景穩定器\n" +
                "AH-IPS 先進高效能顯示面板\n" +
                "低藍光不閃爍，德國 TUV 南德認證\n" +
                "LG 專屬軟體：OSC 滑鼠控制選單功能\n" +
                "搖捍式選單快速控制鍵"
        data7.type = 1

        val data8 = ProdData()
        data8.name = "Asus ROG Strix XG32VQR 32型曲面電競螢幕"
        data8.id = 8
        data8.imageUrl = "https://d.ecimg.tw/items/DSAB0BA9009UNR7/000001_1599442849.jpg"
        data8.price = 13000
        data8.discount = 12888
        data8.detail = "2560*1440 2K解析/1800R曲面/FreeSync2/4ms/144Hz\n" +
                "2560X1440 2K超高解析度\n" +
                "1800R 曲面弧度\n" +
                "支援125% sRGB色域\n" +
                "支援144Hz 超高速畫面更新率\n" +
                "支援AMD RADEON FreeSync2技術\n" +
                "HDR 400認證\n" +
                "支援HDMI 2.0*2/DP 1.2/USB 3.0*2\n" +
                "ASUS 獨家 GamePlus 快捷鍵\n" +
                "內建ASUS AURA Sync燈光效果\n" +
                "客製化Light signature功能\n" +
                "華碩獨家DisplayWidget介面，可智慧調整 達成最佳模式\n" +
                "支援VESA壁掛\n" +
                "可調整傾斜與上下左右調整功能\n" +
                "擁有超不閃屏和超低藍光技術"
        data8.type = 1

        val data9 = ProdData()
        data9.name = "BenQ 23型IPS光智慧玩色螢幕 (GW2381)"
        data9.id = 9
        data9.imageUrl = "https://d.ecimg.tw/items/DSABF2A900A7696/000001_1595555741.jpg"
        data9.price = 3000
        data9.discount = 2988
        data9.detail = "IPS 178度廣視角面板\n" +
                "1920x1200 解析\n" +
                "16:10 黃金比例\n" +
                "支援D-sub/HDMI 1.4/Display\n" +
                "低藍光不閃屏\n" +
                "內建喇叭\n" +
                "三年到府收送"
        data9.type = 1


        val data10 = ProdData()
        data10.name = "Asus 32型VA廣視角曲面螢幕(VA327H)"
        data10.id = 10
        data10.imageUrl = "https://c.ecimg.tw/items/DSAADM1900AA2EZ/000001_1569478908.jpg"
        data10.price = 9000
        data10.discount = 8888
        data10.detail = "1800R VA曲面面板\n" +
                "支援D-SUB/HDMI*2輸入介面\n" +
                "不閃屏.低藍光\n" +
                "178度超廣可視角\n" +
                "1920X1080 解析度\n" +
                "100,000,000:1高動態對比\n" +
                "內建喇叭\n" +
                "支援壁掛\n" +
                "三年保固"
        data10.type = 1

        //鍵盤
        val data11 = ProdData()
        data11.name = "Razer Cynosa Lite 薩諾狼蛛Lite版鍵盤"
        data11.id = 11
        data11.imageUrl = "https://d.ecimg.tw/items/DCAH8XA900AOCSG/000001_1603331958.jpg"
        data11.price = 1500
        data11.discount = 1490
        data11.detail = "● 柔軟緩衝的電競級按鍵\n" +
                "● 10鍵齊發不衝突防鬼鍵\n" +
                "● 1000Hz超快輪詢率\n" +
                "● 防潑水耐用設計"
        data11.type = 2

        val data12 = ProdData()
        data12.name = "羅技 MK315 無線靜音鍵盤滑鼠"
        data12.id = 12
        data12.imageUrl = "https://d.ecimg.tw/items/DCAH86A9009JR8H/000001_1591668429.jpg"
        data12.price = 1000
        data12.discount = 949
        data12.detail = "● 無聲鍵盤與滑鼠\n" +
                "● 舒適的打字與點按\n" +
                "● 耐用的鍵盤滑鼠組合\n" +
                "● 可靠的無線連線"
        data12.type = 2

        val data13 = ProdData()
        data13.name = "羅技 K380 跨平台藍牙鍵盤(藍)"
        data13.id = 13
        data13.imageUrl = "https://e.ecimg.tw/items/DSAR21A9006T8NS/000001_1586486453.jpg"
        data13.price = 1000
        data13.discount = 990
        data13.detail = "．可在任何裝置上打字的通用鍵盤\n" +
                "．在裝置間輕鬆切換\n" +
                "．舒適的打字體驗\n" +
                "．便攜設計"
        data13.type = 2

        val data14 = ProdData()
        data14.name = "羅技 G613 無線機械式遊戲鍵盤"
        data14.id = 14
        data14.imageUrl = "https://b.ecimg.tw/items/DCAH83A9008EMKQ/000001_1596191888.jpg"
        data14.price = 3000
        data14.discount = 2990
        data14.detail = "● Romer-G 機械軸\n" +
                "● 可自訂G功能鍵\n" +
                "● 無線 / 藍牙連線技術\n" +
                "● 兩顆3號AA電池，電力續航最高可達18個月"
        data14.type = 2


        val data15 = ProdData()
        data15.name = "羅技 G610 Orion Blue 背光機械遊戲鍵盤"
        data15.id = 15
        data15.imageUrl = "https://d.ecimg.tw/items/DCAH83A9007CA15/000001_1603159313.jpg"
        data15.price = 3000
        data15.discount = 2990
        data15.detail = "◆ Cherry MX 機械式青軸軸承\n" +
                "◆ 可自訂白色背光系統\n" +
                "◆ ARX 控制整合\n" +
                "◆ 人體工學鍵帽設計\n" +
                "◆ 原廠2年硬體保固"
        data15.type = 2

        //筆電
        val data16 = ProdData()
        data16.name = "ASUS VivoBook X413FA-0051K10210U 酷玩黑"
        data16.id = 16
        data16.imageUrl = "https://d.ecimg.tw/items/DHAFKJA900ANC79/000001_1601256101.jpg"
        data16.price = 22000
        data16.discount = 21900
        data16.detail = "LCD尺寸：14\"FHD IPS寬螢幕 (LED) 四邊窄邊框設計\n" +
                "處理器：Intel® Core™ i5-10210U Processor 1.6 GHz\n" +
                "記憶體：8GB*1 DDR4\n" +
                "硬碟：512GB M.2 NVMe™ PCIe® 3.0 SSD\n" +
                "網路：Wi-Fi 6(Gig+)(802.11ax)+Bluetooth 5.0 (Dual band) 2*2\n" +
                "重量：1.4KG\n" +
                "特色：USB 3.2 Type C、178度廣視角、87%屏佔比\n" +
                "作業系統： 64 Bits Windows 10 Home"
        data16.type = 3

        val data17 = ProdData()
        data17.name = "ASUS S14 S432FL-0082E8265U 超能綠"
        data17.id = 17
        data17.imageUrl = "https://d.ecimg.tw/items/DHAFJAA900AIWC4/000001_1603329702.jpg"
        data17.price = 24000
        data17.discount = 23900
        data17.detail = "LCD尺寸：15.6\" FHD 螢幕 四邊窄邊框設計\n" +
                "處理器：Intel® Core™ i5-8265U Processor 1.6 GHz\n" +
                "記憶體：LPDDR 3 2133 8G (Max 8G)\n" +
                "顯卡：Nvidia MX 250 2G獨顯\n" +
                "硬碟：PCIEG3x2 NVME 512GB M.2 SSD\n" +
                "網路：Wi-Fi 5(802.11ac)+Bluetooth 4.2 (Dual band) 2*2\n" +
                "重量：1.45 KG\n" +
                "特色：含背光KB、USB3.1 Type C、HDMI\n" +
                "作業系統： 64 Bits Windows 10 Home"
        data17.type = 3

        val data18 = ProdData()
        data18.name = "ASUS ZenBook 14 UX425JA 綠松灰"
        data18.id = 18
        data18.imageUrl = "https://e.ecimg.tw/items/DHAX97A900AQ21N/000001_1597471407.jpg"
        data18.price = 32000
        data18.discount = 31900
        data18.detail = "LCD尺寸：14.0’//300nits//FHD 1920x1080 16:9//Anti-Glare\n" +
                "處理器：Intel® Core™ i5-1035G1 1.0 GHz\n" +
                "記憶體：8GB LPDDR4X on board\n" +
                "顯示晶片：Intel® UHD Graphics\n" +
                "硬碟：512GB M.2 NVMe™ PCIe® 3.0 SSD\n" +
                "其他：四邊NonaEdge極窄邊框、NumberPad 2.0、紅外線 (IR) 網路攝影鏡頭支援臉部辨識、通過MIL-STD-810G 軍規標準、22 小時電池續航力\n" +
                "網路：802.11ax+Bluetooth 5.0 (Dual band) 2*2\n" +
                "重量：1.13 kg\n" +
                "作業系統： 64 Bits Windows 10 Home\n" +
                "保固：2年全球保固 LCD無亮點保固"
        data18.type = 3

        val data19 = ProdData()
        data19.name = "ASUS VivoBook S403FA-0232C10210U玫瑰金"
        data19.id = 19
        data19.imageUrl = "https://d.ecimg.tw/items/DHAFIRA900AEYQR/000001_1582081964.jpg"
        data19.price = 28000
        data19.discount = 27900
        data19.detail = "LCD尺寸：14\" FHD 螢幕 (LED) 窄邊框設計\n" +
                "處理器：Intel® Core™ i5-10210U 1.6 GHz\n" +
                "記憶體： 8G (On board)\n" +
                "硬碟：PCIEG3x2 NVME 512GB M.2 SSD\n" +
                "網路：Wi-Fi 5(802.11ac)+Bluetooth 4.2 (Dual band) 2*2\n" +
                "重量：1.35 KG\n" +
                "特色：USB 3.1 Type C、24小時超長電池續航力、通過軍規測試\n" +
                "作業系統： 64 Bits Windows 10 Home"
        data19.type = 3


        val data20 = ProdData()
        data20.name = "ASUS X571LH星夜黑15.6吋窄邊10代i7電競筆電"
        data20.id = 20
        data20.imageUrl = "https://d.ecimg.tw/items/DHAFHQA900APJJ2/000001_1601860770.jpg"
        data20.price = 35000
        data20.discount = 33900
        data20.detail = "LCD尺寸：15.6 FHD 窄邊框\n" +
                "處理器：Intel® Core™ i7-10750H  2.6 GHz \n" +
                "記憶體：8GB DDR4 on board\n" +
                "硬碟：1TB HDD+256GB SSD\n" +
                "顯卡：NVIDIA GTX 1650 4G 獨顯\n" +
                "網路：Wi-Fi 6(Gig+)(802.11ax)+Bluetooth 5.0 (Dual band) 2*2\n" +
                "重量：2.14kg\n" +
                "作業系統： 64 Bits Windows 10 Home"
        data20.type = 3

        mData1?.add(data1)
        mData1?.add(data2)
        mData1?.add(data3)
        mData1?.add(data4)
        mData1?.add(data5)

        mData1?.add(data6)
        mData1?.add(data7)
        mData1?.add(data8)
        mData1?.add(data9)
        mData1?.add(data10)

        mData1?.add(data11)
        mData1?.add(data12)
        mData1?.add(data13)
        mData1?.add(data14)
        mData1?.add(data15)

        mData1?.add(data16)
        mData1?.add(data17)
        mData1?.add(data18)
        mData1?.add(data19)
        mData1?.add(data20)

        return mData1!!
    }

    //熱銷ProdDat
    private fun setData3(): ArrayList<ProdData> {

        //主機
        val data1 = ProdData()
        data1.name = "Nintendo Switch 藍紅主機"
        data1.id = 1
        data1.imageUrl = "https://f.ecimg.tw/items/DGBJDE1900AB40Y/000001_1588563364.jpg"
        data1.price = 10000
        data1.discount = 9580
        data1.detail = "▉ 可遊玩時間加長!!!\n" +
                "▉ SWITCH & PLAY 遊戲生活變得更加互動\n" +
                "▉ 改變形態多種遊戲模式：TV模式、桌上模式、手提模式\n" +
                "▉ 最多連線8台主機，進行對戰或協力遊戲\n" +
                "▉ Joy-Con內置「HD震動」體驗逼真細膩臨場感\n" +
                "▉ 台灣公司貨，提供1年保固。"
        data1.type = 0

        val data2 = ProdData()
        data2.name = "任天堂 Switch 紅藍主機 + Joy-Con +精選遊戲 超值組"
        data2.id = 2
        data2.imageUrl = "https://e.ecimg.tw/items/DGBJDE1900ATV3G/000001_1603677803.jpg"
        data2.price = 16000
        data2.discount = 15150
        data2.detail = "NS新型號主機-電池持續時間加長!!!\n" +
                "▉ 最多連線8台，與親朋好友協力對戰\n" +
                "▉ 多種操控模式及玩法，樂享家中大螢幕或外攜都適合\n" +
                "▉ Joy-Con內置「HD震動」體驗逼真細膩臨場感\n" +
                "◆ 支援Nintendo Switch Online (※付費，台灣地區暫未支援)\n" +
                "◆ 支援繁體中文介面\n" +
                "◆ 台灣公司貨，提供1年保固。"
        data2.type = 0

        val data3 = ProdData()
        data3.name = "PS4 Pro-1TB《極致黑》雙手把遊戲主機"
        data3.id = 3
        data3.imageUrl = "https://d.ecimg.tw/items/DGBJA7A900AGBTE/000001_1603681782.jpg"
        data3.price = 10000
        data3.discount = 9980
        data3.detail = "《絕不退讓★全場1.1折起》\n" +
                "開始：１０／２６（一）１１：００\n" +
                "結束：１１／０２（一）１０：５９\n" +
                "網路價\$12980．限時價↘\$９９８０\n" +
                "\n" +
                "■全新CUH-7200系列\n" +
                "■硬體容量為1TB\n" +
                "■支援 4K 超高解析度\n" +
                "■PS4 遊戲均以1080p 解像度呈現\n" +
                "■支援已發售及將會發售的所有PS4遊戲"
        data3.type = 0

        val data4 = ProdData()
        data4.name = "Switch 動物森友會特別版公司貨主機+健身環大冒險+動森便攜包+任選遊戲x1"
        data4.id = 4
        data4.imageUrl = "https://b.ecimg.tw/items/DGBJDE1900AV9K8/000001_1599817296.jpg"
        data4.price = 16000
        data4.discount = 15590
        data4.detail = "集合啦！動物森友會 特仕版主機 (不含遊戲)\n" +
                "台灣公司貨主機\n" +
                "主機本體保固一年\n" +
                "支援繁體中文介面\n"
        data4.type = 0


        val data5 = ProdData()
        data5.name = "SEGA 迷你復刻 Mega Drive Mini 主機 (收錄42款經典名作)-台灣公司貨"
        data5.id = 5
        data5.imageUrl = "https://d.ecimg.tw/items/DGBJ70A900A9BSX/000001_1601432208.jpg"
        data5.price = 2000
        data5.discount = 1777
        data5.detail = "(收錄42款經典名作)\n" +
                "1988歷久彌新～16bit迷你復刻登場！\n" +
                "■ 42款經典遊戲！盡情遊玩「回憶之作」\n" +
                "■ 尺寸縮小45%～竟然只有手掌大！\n" +
                "■ 可中途保存，隨時中斷也不用擔心\n" +
                "■ HDMI連接大螢幕享受經典回憶\n" +
                "■ 附2隻控制器，可與朋友同歡"
        data5.type = 0

        //螢幕
        val data6 = ProdData()
        data6.name = "DELL P2418HT-3Y 24型IPS觸控寬螢幕"
        data6.id = 6
        data6.imageUrl = "https://e.ecimg.tw/items/DSAB92A90081RP1/000001_1543485718.jpg"
        data6.price = 13000
        data6.discount = 12888
        data6.detail = "十點觸控∥防眩光∥薄邊框設計\n" +
                "IPS 178度廣視角面板\n" +
                "支援D-sub、HDMI1.4、DP1.2介面\n" +
                "USB3.0上行連接埠 / USB3.0 x 2 / USB2.0 x 2\n" +
                "1920*1080 FHD解析\n" +
                "亮度:250cd/㎡\n" +
                "對比率:1000:1\n" +
                "可調整高度、傾斜、左右旋轉\n" +
                "支援壁掛\n" +
                "三年保固\n"
        data6.type = 1

        val data7 = ProdData()
        data7.name = "GeChic On-Lap M505E 15.6吋行動螢幕USB Type-C/ HDMI雙介面(支援HDMI環路輸出)\n"
        data7.id = 7
        data7.imageUrl = "https://e.ecimg.tw/items/DSAB93A900AV2WV/000001_1600851979.jpg"
        data7.price = 7000
        data7.discount = 6490
        data7.detail = "雙打空間不擁擠，對坐更能發揮實力!\n" +
                "具備HDMI輸出，一次可串聯高達14台同步顯示遊戲畫面\uD83D\uDC48\n" +
                "\n" +
                "特惠狂降 →要買要快!\n" +
                "網路價\$7490． 限時特惠價↘\$6490\n" +
                "摺疊收闔設計│具備HDMI IN & OUT │雙介面\n" +
                "超值優惠，把握機會不要錯過!\n" +
                "\n" +
                "背面藏線設計，筆電雙螢幕並排無縫隙!\n" +
                "\n" +
                "● FHD 1080P解析度、高對比\n" +
                "● 170度超廣視角(AAS廣視角面板)\n" +
                "● 具備HDMI 輸入/ 輸出，可一次串聯高達14台螢幕\n" +
                "● HDMI/USB Type-C雙影音傳輸介面\n" +
                "● 背面接線，正面無線頭更清爽!\n" +
                "● 折疊式設計，收闔不佔空間\n" +
                "● 內建雙聲道喇叭\n" +
                "● 隨螢幕附贈保護袋\n" +
                "可搭配Switch,PS4, Xbox ONE S, Xbox ONE, Xbox 360,PS3, 任天堂迷你紅白機, 迷你超任等"
        data7.type = 1

        val data8 = ProdData()
        data8.name = "小米34型曲面螢幕"
        data8.id = 8
        data8.imageUrl = "https://d.ecimg.tw/items/DYAN79A900AUZCF/000001_1599547907.jpg"
        data8.price = 13000
        data8.discount = 12888
        data8.detail = "超廣闊環繞沉浸視野\n" +
                "暢快遊戲娛樂體驗\n" +
                "3年保固 品質保證"
        data8.type = 1

        val data9 = ProdData()
        data9.name = "ASUS 32型4K IPS專業螢幕(PA32UC-K)"
        data9.id = 9
        data9.imageUrl = "https://d.ecimg.tw/items/DSABBIA9008TMHJ/000001_1582277266.jpg"
        data9.price = 65000
        data9.discount = 63990
        data9.detail = "・無邊框設計 ・支援HDR/4K功能 ・Thunderbolt™ 3\n" +
                "IPS 178度超廣視角面板\n" +
                "支援HDMI(v2.0b)x4/DisplayPort 1.2介面\n" +
                "支援USB3.0連接埠\n" +
                "支援Thunderbolt™ 3(資料傳輸速度高達40Gbps)\n" +
                "3840 X 2160 4K超高解析度\n" +
                "支援100% sRGB色域\n" +
                "支援99.5% Adobe RGB色域\n" +
                "支援95% of DCI-P3色域\n" +
                "支援85% Rec.2020色域\n" +
                "支援子母畫面與雙畫面(PiP/PbP)\n" +
                "ASUS ProArt™校正技術\n" +
                "可調整傾斜、高低與水平、垂直旋轉功能\n" +
                "超不閃屏和超低藍光技術\n" +
                "內建喇叭\n" +
                "支援壁掛"
        data9.type = 1


        val data10 = ProdData()
        data10.name = "BenQ 23型16:10 IPS光智慧玩色螢幕 (GW2381)"
        data10.id = 10
        data10.imageUrl = "https://d.ecimg.tw/items/DSABF2A900A74BY/000001_1595555770.jpg"
        data10.price = 3000
        data10.discount = 2988
        data10.detail = "限時促銷→要買要快\n" +
                "開始﹕即刻開始 結束﹕隨時結束\n" +
                "網路價\$4288． 限時價↘\$2988\n" +
                "限時限量 把握機會不要錯過!\n" +
                "\n" +
                "\n" +
                "\n" +
                "光智慧護眼專利/玩色調整色階比例\n" +
                "IPS 178度廣視角面板\n" +
                "1920x1200 解析\n" +
                "16:10 黃金比例\n" +
                "支援D-sub/HDMI 1.4/Display\n" +
                "低藍光不閃屏\n" +
                "內建喇叭\n" +
                "三年到府收送"
        data10.type = 1

        //音響
        val data11 = ProdData()
        data11.name = "JVC 5.1聲道無線家庭劇院 J3851"
        data11.id = 11
        data11.imageUrl = "https://b.ecimg.tw/items/DPAH1ZA9009PF29/000001_1603676607.jpg"
        data11.price = 6500
        data11.discount = 5999
        data11.detail = "《絕不退讓★全場1.1折起》\n" +
                "開始：１０／２６（一）１１：００\n" +
                "結束：１１／０２（一）１０：５９\n" +
                "網路價\$14990．限時價↘\$５９９９\n" +
                "★ 刷指定卡加碼再回饋２%P幣\n" +
                "★ 送CATCHPLAY+電影乙部\n" +
                "\n" +
                "JVC 38吋無線重低音家庭劇院\n" +
                "\n" +
                "♡ 5.1聲道劇院 免萬CP值超高 ♡\n" +
                "★專屬高階JVC液晶顯示搖控器\n" +
                "★聲棒約為38吋(97CM) \"無線\"重低音約為4.5吋(12.7CM)\n" +
                "\n" +
                "★★ 本機為最新上市熱銷款，選SOUNDBAR\n" +
                "要知道尺吋長度與喇叭數，這關系到音場與音壓密度 ★★\n" +
                "\n" +
                "===============================================\n" +
                "★喇叭單體總數為8個，前置SoundBar內建5個全音域單體\n" +
                "★藍芽/光纖/同軸/AUX/RCA/LINE-IN/USB 支援\n" +
                "===============================================\n" +
                "★標配JVC液晶搖控器/壁掛套件/安裝輔助模版/束線帶\n" +
                "★具備Bluetooth 藍芽無線輸入功能,可快速連接使用\n" +
                "===============================================\n" +
                "★採DTS &Dolby Digital 音效處理技術可呈現完美音場表現\n" +
                "★\"無線\"重低音SUPER BASS 環境音場/聲臨其境/重返現場\n" +
                "===============================================\n" +
                "\n" +
                "★前置尺寸(寬×高×深):96.9 x 7.87 x 7.62 cm / 2.98 kg\n" +
                "★重低音尺吋(寬×高×深):21.1 x 23.1 x 21.1 cm / 3.24 kg\n" +
                "★環繞尺寸(寬×高×深):7.62 x 15.12 x 7.62 cm / 0.57 kg"
        data11.type = 4

        val data12 = ProdData()
        data12.name = "harman/kardon Aura Studio 3 無線藍牙喇叭\n" +
                "經典水母喇叭第三代"
        data12.id = 12
        data12.imageUrl = "https://e.ecimg.tw/items/DCAID9A900AP120/000001_1591163478.jpg"
        data12.price = 12000
        data12.discount = 11990
        data12.detail = "★世貨總代理_公司貨★\n" +
                "◉360° 環繞立體音效 6個40mm中高頻單體\n" +
                "◉支援藍牙無線串流音樂\n" +
                "◉全新波紋動態燈光 360° LED呼吸燈\n" +
                "◉經典透明水母造型 宛若現代藝術精品\n" +
                "◉採用100W低音單體 低音下潛深且震撼\n" +
                "◉藍牙4.2連接 便利音頻輸入接口"
        data12.type = 4

        val data13 = ProdData()
        data13.name = "Edifier R1280DB 2.0聲道藍牙喇叭"
        data13.id = 13
        data13.imageUrl = "https://d.ecimg.tw/items/DCAI2UA900A3AQK/000001_1568116803.jpg"
        data13.price = 3000
        data13.discount = 2790
        data13.detail = "● 多選擇的音頻輸入：藍牙、3.5mm、3.5mm to RCA、光纖/同軸\n" +
                "● 無線遙控器全新設計\n" +
                "● 箱側高、低、音量旋鈕調控\n" +
                "● 木質箱體結構"
        data13.type = 4

        val data14 = ProdData()
        data14.name = "SONY 3.1聲道 Dolby Atmos 單件式喇叭\n" +
                "HT-G700"
        data14.id = 14
        data14.imageUrl = "https://f.ecimg.tw/items/DMAAECA900APDZX/000001_1591685415.jpg"
        data14.price = 16000
        data14.discount = 15900
        data14.detail = "2020新機上市\n" +
                "➤3.1 聲道支援Dolby Atmos/DTS:X\n" +
                "➤總輸出400W，具備強力的無線重低音喇叭\n" +
                "➤具有HDMI eARC/ARC，連結電視方便快速\n" +
                "➤可透過藍牙方式與電視無線連線\n" +
                "➤水平環繞S-Force PRO，能配合電視高度的影音合一體驗\n" +
                "➤強化影片和音樂體驗的自選音效模式\n" +
                "➤主機身大小–僅機身 (寬 X 高 X 深) 980 x 64 x 108 mm／重低音喇叭尺寸 (寬 X 高 X 深) 192 x 387 x 406 mm\n" +
                "➤原廠保固一年"
        data14.type = 4


        val data15 = ProdData()
        data15.name = "JBL GO 2 可攜式防水藍牙喇叭(星鑽灰)"
        data15.id = 15
        data15.imageUrl = "https://e.ecimg.tw/items/DCAIDCA90099X3C/000001_1533197220.jpg"
        data15.price = 2000
        data15.discount = 1880
        data15.detail = "◆ 無線藍牙串流技術\n" +
                "◆ 播放時間達5小時\n" +
                "◆ 防水設計\n" +
                "◆ 手機擴音喇叭功能\n" +
                "◆ 支援外接音源輸入播放\n" +
                "\n" +
                "▼更多顏色可選▼\n" +
                "午夜黑\n" +
                "寶石紅\n" +
                "肉桂粉\n" +
                "珊瑚橘\n" +
                "萊姆黃\n" +
                "薄荷綠\n" +
                "青苔綠\n" +
                "冰塊藍\n" +
                "深海藍\n" +
                "海軍藍\n" +
                "香檳金"
        data15.type = 4

        //筆電
        val data16 = ProdData()
        data16.name = "Lenovo L340 Gaming 81LK01JKTW 經典黑"
        data16.id = 16
        data16.imageUrl = "https://f.ecimg.tw/items/DHBA0V1900AU1U4/000001_1602815327.jpg"
        data16.price = 26000
        data16.discount = 22990
        data16.detail = "\uD83C\uDF1F\uD83C\uDFAE舒適冷光鍵盤獨立數字鍵\uD83C\uDFAE\uD83C\uDF1F\n" +
                "\uD83C\uDF1F\uD83D\uDD0B快速回血1小時 80% 快充\uD83D\uDD0B\uD83C\uDF1F\n" +
                "\n" +
                "\uD83E\uDD1F專業電競GTX GDDR6獨立顯卡\n" +
                "\uD83E\uDD1F優良散熱雙風扇雙導管雙鰭片\n" +
                "\uD83E\uDD1FFN+Q智慧效能快速切換\n" +
                "\uD83E\uDD1F速度及容量兼備雙碟\n" +
                "\uD83E\uDD1F杜比環繞喇叭最動聽\n" +
                "\uD83E\uDD1F隨身便攜重量僅2.2Kg\n" +
                "\n" +
                "處理器：Intel® CORE I5-9300HF 2.4G\n" +
                "記憶體：8GB_DDR4_2400_SODIMM\n" +
                "硬碟：512GB_M.2_2280_NVME_TLC\n" +
                "顯示晶片：GTX 1650 4GB獨顯\n" +
                "螢幕：15.6\" FHD IPS (1920 x 1080)\n" +
                "光碟機：N\n" +
                "作業系統：Windows 10 HOME 64bits\n" +
                "保固：2年台灣保固/加贈第一年全面保障計畫"
        data16.type = 3

        val data17 = ProdData()
        data17.name = "ACER Nitro5 AN515-55-53CZ 黑\n" +
                "i5-10300H∥1T+256G PCIe∥ FHD"
        data17.id = 17
        data17.imageUrl = "https://e.ecimg.tw/items/DHAEGB1900ASM9T/000001_1603158167.jpg"
        data17.price = 28000
        data17.discount = 27900
        data17.detail = "處理器：Intel® Core™ i5-10300H\n" +
                "記憶體：8GB DDR4\n" +
                "螢幕：15.6\" FHD/IPS/144Hz/霧面/LED背光\n" +
                "硬碟：1T+256G PCIe SSD\n" +
                "顯卡：NVIDIA® GeForce® GTX 1650\n" +
                "無線網路：802.11a/b/g/n/acR2+ax 2x2 MU-MIMO\n" +
                "其他：USB 3.0、HDMI\n" +
                "作業系統：Windows 10 Home\n" +
                "保固：台灣2年"
        data17.type = 3

        val data18 = ProdData()
        data18.name = "MSI微星 GF63 9SCSR-895TW 15.6吋電競筆電\n" +
                "i5-9300H ∥ GTX1650Ti-4G ∥ 512G ∥ 144Hz"
        data18.id = 18
        data18.imageUrl = "https://d.ecimg.tw/items/DHAK8IA900AUYL9/000001_1603676178.jpg"
        data18.price = 30000
        data18.discount = 29900
        data18.detail = "《 超狂特色。飛炫到不行 》\n" +
                "★搭載新一代GeForce GTX 1650Ti Max-Q 獨立顯示卡\n" +
                "★窄邊框面板，更新率144Hz\n" +
                "★全新Dragon Center 軟體提供獨家電競模式2.0\n" +
                "★支援播放Hi-Fi 高解析音樂\n" +
                "★7小時電池續航力"
        data18.type = 3

        val data19 = ProdData()
        data19.name = "技嘉 AORUS 7 KB 17吋電競筆電\n" +
                "i7-10750H ∥ RTX2060-6G ∥ 16G ∥ 1TB+512G SSD ∥ 144Hz"
        data19.id = 19
        data19.imageUrl = "https://c.ecimg.tw/items/DHAV5TA900AX4G6/000001_1603682663.jpg"
        data19.price = 37000
        data19.discount = 36900
        data19.detail = "五大推薦入手理由\n" +
                "1. 最新十代高效能CPU ✌ 暢玩3A遊戲大作\n" +
                "2. 夢幻顯卡，光影追蹤RTX2060 ✌ 尊爵高貴而不貴\n" +
                "3. 高速16G記憶體搭配混碟大容量 ✌ 遊戲順暢不卡頻\n" +
                "4. 廣視角IPS 144hz螢幕刷新率 ✌ 分秒皆是精彩視覺享受\n" +
                "5. 正港MIT製造 ✌ 安心兩年全球保固\n" +
                "\n" +
                "主要規格\n" +
                "•處理器: 第10代 Intel i7-10750H(2.6GHz-5GHz)\n" +
                "•顯示卡: 電競級 GeForce RTX 2060 6G 顯卡\n" +
                "•螢幕: 17.3吋 FHD 144Hz高更新率電競螢幕\n" +
                "NTSC 72% 廣色域 還原真實遊戲色彩\n" +
                "•記憶體: 16GB DDR4 2666\n" +
                "•儲存空間: M.2 PCIe 512G SSD + 1TB HDD\n" +
                "•作業系統: Windows 10 Home\n" +
                "•鍵盤: 15色全區孤島背光鍵盤 (中文鍵盤)\n" +
                "•電池: 充電式鋰電池 48.96Wh\n" +
                "•重量: 2.5KG\n" +
                "•保固: 全球兩年保固 (技嘉服務中心)"
        data19.type = 3


        val data20 = ProdData()
        data20.name = "15.6吋10代薄邊框電競\n" +
                "【ROG Zephyrus】\n" +
                "Zephyrus S15 GX502LWS-0021A10875H 黑"
        data20.id = 20
        data20.imageUrl = "https://c.ecimg.tw/items/DHAS4NA900AUYTE/000001_1600163199.jpg"
        data20.price = 70000
        data20.discount = 68900
        data20.detail = "▃▅★ROG Zephyrus S15。強勢來襲★▅▃\n" +
                "★全新顯卡視覺享受 RTX2070 Super獨顯8G效能\n" +
                "★超屌效能 搭載2020新世代 Intel i7-10875H 處理器\n" +
                "★超狂容量 1TB PCIE SSD\n" +
                "★全新升級 薄邊框螢幕 300Hz\n" +
                "★15.6吋 FHD IPS 大屏佔比\n" +
                "★Pantone色彩驗證/PD充電技術\n" +
                "★液態金屬散熱/RGB鍵盤\n" +
                "\n" +
                "處理器：Intel® Core™ i7-10875H Processor 2.3 GHz\n" +
                "記憶體：16GB DDR4 on board\n" +
                "硬碟：1TB M.2 NVMe™ PCIe® 3.0 Performance SSD\n" +
                "獨立顯卡：NVIDIA® GeForce RTX™ 2070 Super獨顯\n" +
                "螢幕：15.6\" FHD IPS 1920x1080 薄邊框螢幕\n" +
                "無線網路：Wi-Fi 6(Gig+)(802.11ax)+Bluetooth 5.1 (Dual band) 2*2\n" +
                "光碟機： 無\n" +
                "介面：HDMI、Thunderbolt™ 3\n" +
                "電池：76WHrs, 4S1P, 4-cell Li-ion\n" +
                "重量：1.9 KG\n" +
                "厚度：1.89cm\n" +
                "作業系統：Windows 10 (64bit)\n" +
                "保固：2年全球保固 + 首年免預付完美保固"
        data20.type = 3

        mData1?.add(data1)
        mData1?.add(data2)
        mData1?.add(data3)
        mData1?.add(data4)
        mData1?.add(data5)

        mData1?.add(data6)
        mData1?.add(data7)
        mData1?.add(data8)
        mData1?.add(data9)
        mData1?.add(data10)

        mData1?.add(data11)
        mData1?.add(data12)
        mData1?.add(data13)
        mData1?.add(data14)
        mData1?.add(data15)

        mData1?.add(data16)
        mData1?.add(data17)
        mData1?.add(data18)
        mData1?.add(data19)
        mData1?.add(data20)

        return mData1!!
    }

    //HotCampingData 熱門活動
    private fun setData4(): ArrayList<ProdData> {

        val data1 = ProdData()
        data1.name = "雙11必買推薦｜小米/ 小米掃地機器人/ 米家掃地機器人/小瓦智慧掃地機器人"
        data1.id = 1
        data1.imageUrl = "https://content.shopback.com/tw/wp-content/uploads/2019/11/05143947/vacuum-mi.jpg"
        data1.price = 29990
        data1.discount = 25000
        data1.detail = "懶人、孝親必備——小米掃地機器人！米家掃地機器人是雙11大熱門商品，一直以來在掃地機器人中評價都很高的米家掃地機器人，是兼具美觀、CP值、性能的好物！想要省荷包就趁雙11熱潮購入吧～"
        data1.type = 6

        val data2 = ProdData()
        data2.name = "雙11必買推薦｜Dyson /Dyson V8 / Dyson V10/ Dyson V11，吸塵器、吹風機"
        data2.id = 2
        data2.imageUrl = "https://content.shopback.com/tw/wp-content/uploads/2018/03/24004538/1-1-e1521823616802.jpg"
        data2.price = 18000
        data2.discount = 15000
        data2.detail = "你知道買Dyson也能賺ShopBack現金回饋嗎？Dyson是每個家庭夢寐以求的超強商品，幾乎終年不特價，想要買便宜，最好就是趁雙11搭配ShopBack購入！Dyson V8、Dyson V10 吸塵器，或是 Dyson Supersonic 吹風機、空氣清淨機、冷暖扇…，平常捨不得下手的都趁現在，雙11買Dyson最便宜！"
        data2.type = 6

        val data3 = ProdData()
        data3.name = "雙11必買推薦｜PS4/ PS4 pro"
        data3.id = 3
        data3.imageUrl = "https://content.shopback.com/tw/wp-content/uploads/2019/11/05154502/SONY-PS4-Pro.jpg"
        data3.price = 16000
        data3.discount = 13000
        data3.detail = "PS4是不管你邀請朋友來家裡，或是自己一人想殺時間，都非常好玩的遊戲機！價格有點高沒關係，就趁雙11買PS4吧，連機帶遊戲一起購入，魔物獵人 世界、Final Fantasy XV、尼爾：自動人形、湯姆克蘭西：全境封鎖….下班、假日就玩PS4玩爽爽囉，雙11買PS4，也算是為（無聊的）春節過年做準備囉。"
        data3.type = 0

        val data4 = ProdData()
        data4.name = "雙11必買推薦｜Honeywell 空氣清淨機"
        data4.id = 4
        data4.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602667934_201408A1800001.jpg"
        data4.price = 20000
        data4.discount = 18000
        data4.detail = "這台網路超夯爆款美國 Honeywell空氣清淨機+連續7年的空氣清淨機銷售冠軍，你跟上了嗎？空污問題越來越嚴重，你需要Honeywell空氣清淨機來照顧家人的身體健康，這台空氣清淨機高貴不貴，趁雙11買最划算！透過ShopBack買Honeywell，賺一波現金回饋+讓家裡的空氣清新，過敏掰掰！"
        data4.type = 6

        val data5 = ProdData()
        data5.name = "雙11必買推薦｜任天堂 Switch主機/Switch遊戲"
        data5.id = 5
        data5.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1601264969_201408A1800001.jpg"
        data5.price = 15000
        data5.discount = 7500
        data5.detail = "Switch主機不含遊戲價格大約在9000元左右，但雙11可以趁ShopBack加碼現金回饋時大買一波喔！PChome 24h、Yahoo、蝦皮商城…你習慣的購物平台我們通通有，只要在購物前先連到ShopBack，就能輕輕鬆鬆賺現金！如果你已經有Switch主機，也可以趁機補充Switch遊戲，瑪利歐網球 王牌高手、漆彈大作戰2、任天堂明星大亂鬥…超好玩任天堂遊戲都在這，雙11買Switch就對啦～"
        data5.type = 0

        mData?.add(data1)
        mData?.add(data2)
        mData?.add(data3)
        mData?.add(data4)
        mData?.add(data5)

        return mData!!
    }

    //ProdDat
    private fun setData5(): ArrayList<ProdData> {
        //主機
        val data1 = ProdData()
        data1.name = "Creator P100X 10SE-252TW▼輕薄的品牌主機▼"
        data1.id = 1
        data1.imageUrl = "https://b.ecimg.tw/items/DSAA6IA900AUX1G/000001_1599467197.jpg"
        data1.price = 100000
        data1.discount = 99900
        data1.detail = "《 超狂特色》\n" +
                "★搭載最新第10代英特爾 酷睿i9-10900K處理器\n" +
                "★搭載MSI GeForce RTX 2080 SUPER 8GB GDDR6顯示卡\n" +
                "★採用DDR4 Boost技術的雙通道內存：提供最流暢，最快速的即時預覽\n" +
                "★內建Thunderbolt 3連接阜\n" +
                "★內建Wi-Fi6，速度高達2.4Gbps\n" +
                "★支援2x M.2 PCI-e SSD、2x2.5“SATA儲存裝置\n" +
                "★支援5K2K視覺體驗\n" +
                "★支援專業音質的音頻增強技術\n" +
                "★獨家軟體– Creator Center、Creator OSD\n" +
                "★輕薄的品牌主機，體積僅10.36升"
        data1.type = 0

        val data2 = ProdData()
        data2.name = "Creator P100A 10SI-256TW▼輕薄的品牌主機▼"
        data2.id = 2
        data2.imageUrl = "https://d.ecimg.tw/items/DSAA6IA900AUXLH/000001_1599473089.jpg"
        data2.price = 45000
        data2.discount = 43900
        data2.detail = "《 超狂特色》\n" +
                "★搭載最新第10代英特爾 酷睿i5-10400F處理器\n" +
                "★搭載MSI GeForce GTX 1660 SUPER 6GB GDDR6顯示卡\n" +
                "★採用DDR4 Boost技術的雙通道內存：提供最流暢，最快速的即時預覽\n" +
                "★內建Thunderbolt 3連接阜\n" +
                "★內建Wi-Fi6，速度高達2.4Gbps\n" +
                "★支援2x M.2 PCI-e SSD、2x2.5“SATA儲存裝置\n" +
                "★支援5K2K視覺體驗\n" +
                "★支援專業音質的音頻增強技術\n" +
                "★獨家軟體– Creator Center、Creator OSD\n" +
                "★輕薄的品牌主機，體積僅10.36升"
        data2.type = 0

        val data6 = ProdData()
        data6.name = "BenQ 27型IPS玩色螢幕GW2780Plus"
        data6.id = 6
        data6.imageUrl = "https://e.ecimg.tw/items/DSABF2A9009WX9E/000001_1597022612.jpg"
        data6.price = 5100
        data6.discount = 4988
        data6.detail = "★內建玩色模式-大幅提升辨色力★\n" +
                "IPS 178度廣視角面板\n" +
                "1920x1080 FHD解析度\n" +
                "支援D-Sub/HDMI 1.4/DisplayPort介面\n" +
                "光智慧技術\n" +
                "德國萊茵低藍光、不閃屏雙認證\n" +
                "四段式低藍光模式\n" +
                "內建雙喇叭(2W)\n" +
                "輕薄全美型\n" +
                "可調整傾斜\n" +
                "支援壁掛功能\n" +
                "3年保固\n"
        data6.type = 1

        val data7 = ProdData()
        data7.name = "LG 22型AH-IPS電競螢幕 (22MP58VQ-P)"
        data7.id = 7
        data7.imageUrl = "https://e.ecimg.tw/items/DSAB07A9006V140/000001_1600136434.jpg"
        data7.price = 3000
        data7.discount = 2888
        data7.detail = "簡約時尚輕薄機身設計\n" +
                "電競模式：黑暗場景穩定器\n" +
                "AH-IPS 先進高效能顯示面板\n" +
                "低藍光不閃爍，德國 TUV 南德認證\n" +
                "LG 專屬軟體：OSC 滑鼠控制選單功能\n" +
                "搖捍式選單快速控制鍵"
        data7.type = 1

        val data11 = ProdData()
        data11.name = "Razer Cynosa Lite 薩諾狼蛛Lite版鍵盤"
        data11.id = 11
        data11.imageUrl = "https://d.ecimg.tw/items/DCAH8XA900AOCSG/000001_1603331958.jpg"
        data11.price = 1500
        data11.discount = 1490
        data11.detail = "● 柔軟緩衝的電競級按鍵\n" +
                "● 10鍵齊發不衝突防鬼鍵\n" +
                "● 1000Hz超快輪詢率\n" +
                "● 防潑水耐用設計"
        data11.type = 2

        val data12 = ProdData()
        data12.name = "羅技 MK315 無線靜音鍵盤滑鼠"
        data12.id = 12
        data12.imageUrl = "https://d.ecimg.tw/items/DCAH86A9009JR8H/000001_1591668429.jpg"
        data12.price = 1000
        data12.discount = 949
        data12.detail = "● 無聲鍵盤與滑鼠\n" +
                "● 舒適的打字與點按\n" +
                "● 耐用的鍵盤滑鼠組合\n" +
                "● 可靠的無線連線"
        data12.type = 2

        val data16 = ProdData()
        data16.name = "ASUS VivoBook X413FA-0051K10210U 酷玩黑"
        data16.id = 16
        data16.imageUrl = "https://d.ecimg.tw/items/DHAFKJA900ANC79/000001_1601256101.jpg"
        data16.price = 22000
        data16.discount = 21900
        data16.detail = "LCD尺寸：14\"FHD IPS寬螢幕 (LED) 四邊窄邊框設計\n" +
                "處理器：Intel® Core™ i5-10210U Processor 1.6 GHz\n" +
                "記憶體：8GB*1 DDR4\n" +
                "硬碟：512GB M.2 NVMe™ PCIe® 3.0 SSD\n" +
                "網路：Wi-Fi 6(Gig+)(802.11ax)+Bluetooth 5.0 (Dual band) 2*2\n" +
                "重量：1.4KG\n" +
                "特色：USB 3.2 Type C、178度廣視角、87%屏佔比\n" +
                "作業系統： 64 Bits Windows 10 Home"
        data16.type = 3

        val data17 = ProdData()
        data17.name = "ASUS S14 S432FL-0082E8265U 超能綠"
        data17.id = 17
        data17.imageUrl = "https://d.ecimg.tw/items/DHAFJAA900AIWC4/000001_1603329702.jpg"
        data17.price = 24000
        data17.discount = 23900
        data17.detail = "LCD尺寸：15.6\" FHD 螢幕 四邊窄邊框設計\n" +
                "處理器：Intel® Core™ i5-8265U Processor 1.6 GHz\n" +
                "記憶體：LPDDR 3 2133 8G (Max 8G)\n" +
                "顯卡：Nvidia MX 250 2G獨顯\n" +
                "硬碟：PCIEG3x2 NVME 512GB M.2 SSD\n" +
                "網路：Wi-Fi 5(802.11ac)+Bluetooth 4.2 (Dual band) 2*2\n" +
                "重量：1.45 KG\n" +
                "特色：含背光KB、USB3.1 Type C、HDMI\n" +
                "作業系統： 64 Bits Windows 10 Home"
        data17.type = 3

        mData1?.add(data1)
        mData1?.add(data2)

        mData1?.add(data6)
        mData1?.add(data7)

        mData1?.add(data11)
        mData1?.add(data12)

        mData1?.add(data16)
        mData1?.add(data17)

        return mData1!!
    }
}