package com.software.buy3c.api.gson

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
    var HotSaleData: HotSaleData? = null
}
class HomeData {
    var CampingData: ArrayList<CampingData>? = null
    var ProdData: ArrayList<ProdData>? = null
}

class CampingData {
    var name: String? = null
    var id: Int? = null
    var imageUrl: String? = null
    var price: Int? = null
    var discount: Int? = null
}

class ProdData {
    var name: String? = null
    var id: Int? = null
    var imageUrl: String? = null
    var price: Int? = null
    var discount: Int? = null
    var detail: String? = null
    var type: Int? = null
}

class HotSaleData {
    var name: String? = null
    var id: Int? = null
    var imageUrl: String? = null
    var price: Int? = null
    var discount: Int? = null
}