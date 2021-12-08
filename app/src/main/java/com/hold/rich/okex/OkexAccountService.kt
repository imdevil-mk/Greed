package com.hold.rich.okex

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface AccountService {

    //查看账户余额 Get Balance
    @GET("/api/v5/account/balance")
    fun getBalance(
        @HeaderMap headers: Map<String, String>,
        @Query("ccy") ccy: String
    ): Call<ResponseBody>

}