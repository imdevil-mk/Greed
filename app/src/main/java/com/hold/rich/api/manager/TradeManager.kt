package com.hold.rich.api.manager

import com.hold.rich.api.ApiSuccessResponse
import com.hold.rich.api.bean.Order
import com.hold.rich.api.service.TradeService
import javax.inject.Inject

class TradeManager @Inject constructor(
    private val service: TradeService,
) {
    suspend fun getHistoryOrders(
        instType: String,
    ): List<Order> {
        val map = mapOf("instType" to instType)
        return when (val response = service.getHistoryOrders(map)) {
            is ApiSuccessResponse -> response.data
            else -> emptyList()
        }
    }
}