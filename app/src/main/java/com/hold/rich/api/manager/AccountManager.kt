package com.hold.rich.api.manager

import com.hold.rich.api.ApiSuccessResponse
import com.hold.rich.api.bean.BalanceSummary
import com.hold.rich.api.service.AccountService
import com.hold.rich.utils.joinParams
import javax.inject.Inject

private const val TAG = "AccountManager"

class AccountManager @Inject constructor(
    private val service: AccountService,
) {

    suspend fun getBalance(vararg tokens: String): BalanceSummary? {
        return when (val response = service.getBalance(joinParams(*tokens))) {
            is ApiSuccessResponse -> response.data[0]
            else -> null
        }
    }
}