package com.hold.rich

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hold.rich.api.bean.NetworkResponse
import com.hold.rich.api.service.AccountService
import com.hold.rich.api.service.AssetService
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
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val account = retrofit.create(AccountService::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            val request = try {
                account.getBalance("USDT,LUNA")
            } catch (e: Exception) {
                Exception("Network request failed")
            }
            withContext(Dispatchers.Main) {
                when (request) {
                    is NetworkResponse<*> -> findViewById<TextView>(R.id.msg).text =
                        request.data.toString()
                    is Exception -> findViewById<TextView>(R.id.msg).text = request.toString()
                    else -> Log.d(TAG, "onCreate: error type")
                }
            }
        }

        val asset = retrofit.create(AssetService::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            val response = try {
                asset.getSupportCoinList()
            } catch (e: Exception) {
                Exception("Network request failed")
            }

            when(response) {
                is NetworkResponse<*> -> Log.d(TAG, "onCreate: ${response.data}")
                is Exception -> Log.d(TAG, "onCreate: $response")
                else -> Log.d(TAG, "onCreate: error response type")
            }
        }
    }
}