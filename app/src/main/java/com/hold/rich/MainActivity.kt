package com.hold.rich

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hold.rich.okex.AccountBalanceResponse
import com.hold.rich.okex.AccountService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

private const val TAG = "Greedy-MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var okexRetrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = okexRetrofit.create(AccountService::class.java)

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