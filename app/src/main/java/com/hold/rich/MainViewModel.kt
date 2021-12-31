package com.hold.rich

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hold.rich.api.ApiErrorResponse
import com.hold.rich.api.ApiResponse
import com.hold.rich.api.ApiSuccessResponse
import com.hold.rich.api.bean.BalanceSummary
import com.hold.rich.api.bean.SupportCoin
import com.hold.rich.api.service.AccountService
import com.hold.rich.api.service.AssetService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "Greedy-MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    accountService: AccountService,
    assetService: AssetService,
) : ViewModel() {

    val balanceSummary = liveData {
        val response: ApiResponse<List<BalanceSummary>> =
            accountService.getBalance("USDT,LUNA")
        when (response) {
            is ApiSuccessResponse -> emit(response.data[0].totalEq)
            is ApiErrorResponse -> emit(response.toString())
            else -> emit("onCreate: error type")
        }
    }


    val coinList = liveData {
        when (val response: ApiResponse<List<SupportCoin>> = assetService.getSupportCoinList()) {
            is ApiSuccessResponse -> emit(response.data)
            else -> emit(emptyList())
        }
    }
}