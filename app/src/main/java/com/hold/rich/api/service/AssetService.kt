package com.hold.rich.api.service

import com.hold.rich.api.ApiResponse
import com.hold.rich.api.bean.SupportCoin
import retrofit2.http.GET

interface AssetService {

    @GET("/api/v5/asset/currencies")
    suspend fun getSupportCoinList(): ApiResponse<SupportCoin>
}