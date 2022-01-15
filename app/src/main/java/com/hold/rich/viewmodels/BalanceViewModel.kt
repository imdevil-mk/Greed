package com.hold.rich.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hold.rich.api.bean.BalanceSummary
import com.hold.rich.api.manager.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    var accountManager: AccountManager,
) : ViewModel() {

    private val _balanceSummary: MutableLiveData<BalanceSummary> = MutableLiveData()
    val balanceSummary: LiveData<BalanceSummary>
        get() = _balanceSummary

    fun refreshBalance() = viewModelScope.launch {
        _balanceSummary.value = accountManager.getBalance()
    }
}