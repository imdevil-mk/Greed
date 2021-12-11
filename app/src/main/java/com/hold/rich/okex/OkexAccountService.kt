package com.hold.rich.okex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountService {

    //查看账户余额 Get Balance
    @GET("/api/v5/account/balance")
    suspend fun getBalance(
        @Query("ccy") ccy: String
    ): AccountBalanceResponse

}