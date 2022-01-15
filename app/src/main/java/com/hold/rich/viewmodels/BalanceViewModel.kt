package com.hold.rich.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hold.rich.api.manager.AccountManager
import com.hold.rich.api.manager.AssetManager
import com.hold.rich.api.manager.TradeManager
import com.hold.rich.okex.SPOT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    accountManager: AccountManager,
    assetManager: AssetManager,
    tradeManager: TradeManager,
) : ViewModel() {

    val balanceSummary = liveData {
        emit(accountManager.getBalance())
    }

    val coinList = liveData {
        emit(assetManager.getSupportCoinList())
    }

    val orders = liveData {
        emit(tradeManager.getHistoryOrders(SPOT))
    }
}