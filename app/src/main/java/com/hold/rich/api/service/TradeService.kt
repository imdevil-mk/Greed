package com.hold.rich.api.service

import com.hold.rich.api.ApiResponse
import com.hold.rich.api.bean.Order
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TradeService {

    /** 获取最近3个月的已经完结状态的订单数据 */
    @GET("/api/v5/trade/orders-history-archive")
    suspend fun getHistoryOrders(
        @QueryMap map: Map<String, String>
    ): ApiResponse<Order>
}