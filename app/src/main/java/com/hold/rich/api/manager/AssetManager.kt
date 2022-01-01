package com.hold.rich.api.manager

import com.hold.rich.api.ApiResponse
import com.hold.rich.api.ApiSuccessResponse
import com.hold.rich.api.bean.SupportCoin
import com.hold.rich.api.service.AssetService
import javax.inject.Inject

class AssetManager @Inject constructor(
    private val service: AssetService,
) {

    suspend fun getSupportCoinList(): List<SupportCoin> {
        return when (val response: ApiResponse<List<SupportCoin>> = service.getSupportCoinList()) {
            is ApiSuccessResponse -> response.data
            else -> emptyList()
        }
    }
}