package com.hold.rich

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hold.rich.api.manager.AccountManager
import com.hold.rich.api.manager.AssetManager
import com.hold.rich.api.manager.TradeManager
import com.hold.rich.okex.LUNA
import com.hold.rich.okex.SPOT
import com.hold.rich.okex.USDT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "Greedy-MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    accountManager: AccountManager,
    assetManager: AssetManager,
    tradeManager: TradeManager,
) : ViewModel() {

    val balanceSummary = liveData {
        emit(accountManager.getBalance(LUNA, USDT))
    }

    val coinList = liveData {
        emit(assetManager.getSupportCoinList())
    }

    val orders = liveData {
        emit(tradeManager.getHistoryOrders(SPOT))
    }
}