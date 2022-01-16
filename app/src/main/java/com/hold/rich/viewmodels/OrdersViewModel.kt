package com.hold.rich.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hold.rich.api.bean.Order
import com.hold.rich.api.manager.TradeManager
import com.hold.rich.okex.MARGIN
import com.hold.rich.okex.SPOT
import com.hold.rich.okex.SWAP
import com.hold.rich.utils.toMarginInst
import com.hold.rich.utils.toSpotInst
import com.hold.rich.utils.toSwapInst
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private var tradeManager: TradeManager,
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val order: LiveData<List<Order>>
        get() = _orders

    fun fetchOrders(ccy: String) = viewModelScope.launch {
        val result = mutableListOf<Order>()
        val defer = listOf(
            async { result.addAll(tradeManager.getHistoryOrders(SPOT, ccy.toSpotInst())) },
            async { result.addAll(tradeManager.getHistoryOrders(MARGIN, ccy.toMarginInst())) },
            async { result.addAll(tradeManager.getHistoryOrders(SWAP, ccy.toSwapInst())) },
        )
        defer.awaitAll()
        result.sortByDescending { it.fillTime.toFloat() }
        _orders.value = result
    }
}