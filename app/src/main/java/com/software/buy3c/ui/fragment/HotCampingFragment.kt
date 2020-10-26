package com.software.buy3c.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import com.google.firebase.database.*
import com.software.buy3c.ui.BaseFragment
import com.software.buy3c.ui.FragmentPageManager
import com.software.buy3c.R
import com.software.buy3c.api.gson.CampingData
import com.software.buy3c.api.gson.ProdData

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
    private var ref: DatabaseReference? = null
    private var mData: ArrayList<CampingData>? = null
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
        mFpm = FragmentPageManager(mOwnActivity, fragmentManager)
        mData = ArrayList<CampingData>()
        mData1 = ArrayList<ProdData>()
        ref = FirebaseDatabase.getInstance().reference.child("AllData").child("HomeData").child("ProdData")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e("Jason","Hot")
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        ref?.addValueEventListener(postListener)
        setView(view)

    }

    private fun setView(view: View) {
        bt = view.findViewById(R.id.bt)
        bt?.setOnClickListener {
            ref?.setValue(setData2())
            Log.e("Jason","insert")
        }
    }

    //CampingData
    private fun setData(): ArrayList<CampingData> {

        val data1 = CampingData()
        data1.name = "凡購買ROG PHONE3系列手機送ROG炫光保護殼+ROG桌上型遊戲基座】ROG Phone 3 (ZS661KS  12G/512G)"
        data1.id = 1
        data1.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602724405_201408A1800001.jpg"
        data1.price = 29990
        data1.discount = 25000

        val data2 = CampingData()
        data2.name = "Zenfone 7 Pro 十月好禮 翻轉鉅獻 登錄送 1MOEW Stylish 真無線藍牙耳機 "
        data2.id = 2
        data2.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1601460612_201408A1800001.jpg"
        data2.price = 18000
        data2.discount = 15000

        val data3 = CampingData()
        data3.name = "Zenfone 7 Pro 十月好禮 翻轉鉅獻 登錄送三軸穩定器】ZenFone 7 Pro (ZS671KS 8G/256G)"
        data3.id = 3
        data3.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602464987_201408A1800001.jpg"
        data3.price = 16000
        data3.discount = 13000

        val data4 = CampingData()
        data4.name = "ASUS Store 讀賣機種 "
        data4.id = 4
        data4.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1602667934_201408A1800001.jpg"
        data4.price = 20000
        data4.discount = 18000

        val data5 = CampingData()
        data5.name = "戰場遊我罩ROG周邊58折起"
        data5.id = 5
        data5.imageUrl = "https://img-tw1.asus.com/D/deploy/AWC000013/1601264969_201408A1800001.jpg"
        data5.price = 15000
        data5.discount = 7500

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
                Log.e("Jason","HotCamping 購物車 ")
            }
        }
    }
}