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
class HomeData {
    var CampingData: ArrayList<CampingData>? = null
}

class CampingData {
    var Name: String? = null
    var Id: Int? = null
    var ImageUrl: String? = null
    var Price: Int? = null
    var Discount: Int? = null
}