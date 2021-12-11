package com.hold.rich

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hold.rich.okex.AccountBalanceResponse
import com.hold.rich.okex.AccountService
import com.hold.rich.okex.OkexRetrofit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "Greedy-MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = OkexRetrofit.retrofit.create(AccountService::class.java)

        GlobalScope.launch {
            val request = try {
                retrofit.getBalance("USDT")
            } catch (e: Exception) {
                Exception("Network request failed")
            }
            when (request) {
                is AccountBalanceResponse -> Log.d(TAG, "onCreate: $request")
                is Exception -> Log.d(TAG, "onCreate: $request")
                else -> Log.d(TAG, "onCreate: error type")
            }
        }
    }
}