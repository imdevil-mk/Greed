package com.hold.rich

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hold.rich.okex.AccountBalanceResponse
import com.hold.rich.okex.AccountService
import com.hold.rich.okex.OkexRetrofit
import retrofit2.Response

private const val TAG = "Greedy-MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = OkexRetrofit.retrofit.create(AccountService::class.java)

        val request = retrofit.getBalance("USDT")

        request.enqueue(object : retrofit2.Callback<AccountBalanceResponse> {
            override fun onResponse(
                call: retrofit2.Call<AccountBalanceResponse>,
                response: Response<AccountBalanceResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body().toString()}")
            }

            override fun onFailure(call: retrofit2.Call<AccountBalanceResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: fuck")
            }

        })
    }
}