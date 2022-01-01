package com.hold.rich.api.manager

import com.hold.rich.api.ApiSuccessResponse
import com.hold.rich.api.bean.BalanceSummary
import com.hold.rich.api.service.AccountService
import javax.inject.Inject

private const val TAG = "AccountManager"

class AccountManager @Inject constructor(
    private val service: AccountService,
) {

    suspend fun getBalance(vararg tokens: String): BalanceSummary? {
        var ccys = ""
        for (token in tokens) {
            ccys += "$token,"
        }
        if (ccys.length > 1) ccys = ccys.dropLast(1)
        return when (val response = service.getBalance(ccys)) {
            is ApiSuccessResponse -> response.data[0]
            else -> null
        }
    }
}