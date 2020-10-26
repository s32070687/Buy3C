package com.software.buy3c.api.gson

import java.io.Serializable

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/21.
 * #主維護
 * #副維護
 */

class AllData {
    var HomeData: HomeData? = null
    var HotSaleData: ArrayList<ProdData>? = null
    var HotCampingData: ArrayList<ProdData>? = null
    var MemberData: MemberData? = null
}
class HomeData {
    var CampingData: ArrayList<ProdData>? = null
    var ProdData: ArrayList<ProdData>? = null
}

//type 0:主機(電腦，遊戲) 1: 螢幕 2:鍵盤 3:筆電 4:音響 5:手機 6:家電
class ProdData : Serializable {
    var name: String? = null
    var id: Int? = null
    var imageUrl: String? = null
    var price: Int? = null
    var discount: Int? = null
    var detail: String? = null
    var type: Int? = null
}

class MemberData {

}