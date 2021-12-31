package com.hold.rich

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hold.rich.api.bean.NetworkResponse
import com.hold.rich.api.service.AssetService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

private const val TAG = "Greedy-MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit
    lateinit var msg: TextView
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.activity_main)

        msg = findViewById(R.id.msg)

        mainViewModel.balanceSummary.observe(this) {
            msg.text = it
        }

        val asset = retrofit.create(AssetService::class.java)
        lifecycleScope.launch {
            val response = try {
                asset.getSupportCoinList()
            } catch (e: Exception) {
                Exception("Network request failed")
            }

            when (response) {
                is NetworkResponse<*> -> Log.d(TAG, "onCreate: ${response.data}")
                is Exception -> Log.d(TAG, "onCreate: $response")
                else -> Log.d(TAG, "onCreate: error response type")
            }
        }
    }
}