package com.hold.rich

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hold.rich.okex.AccountBalanceResponse
import com.hold.rich.okex.AccountService
import com.hold.rich.okex.OkexRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "Greedy-MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = OkexRetrofit.retrofit.create(AccountService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val request = try {
                retrofit.getBalance("USDT")
            } catch (e: Exception) {
                Exception("Network request failed")
            }
            withContext(Dispatchers.Main) {
                when (request) {
                    is AccountBalanceResponse -> findViewById<TextView>(R.id.msg).text =
                        request.toString()
                    is Exception -> findViewById<TextView>(R.id.msg).text = request.toString()
                    else -> Log.d(TAG, "onCreate: error type")
                }
            }
        }
    }
}