package com.hold.rich

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hold.rich.okex.AccountBalanceResponse
import com.hold.rich.okex.AccountService
import com.hold.rich.okex.HmacSHA256Base64Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.HashMap

private const val TAG = "Greedy-MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val apiKey = BuildConfig.apiKey
        val secretKey = BuildConfig.secretKey
        val passPhrase = BuildConfig.passPhrase

        val baseUrl = "https://www.okex.com/"


        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccountService::class.java)

        val headers = HashMap<String, String>()

        val timestamp = getUnixTime()
        val method = "GET"
        val requestPath = "/api/v5/account/balance?ccy=USDT"

        val sign = HmacSHA256Base64Utils.sign(timestamp, method, requestPath, null, null, secretKey)

        headers["accept"] = "application/json"
        headers["Content-Type"] = "application/json"
        headers["OK-ACCESS-KEY"] = apiKey
        headers["OK-ACCESS-SIGN"] = sign
        headers["OK-ACCESS-TIMESTAMP"] = timestamp
        headers["OK-ACCESS-PASSPHRASE"] = passPhrase

        val request = retrofit.getBalance(headers, "USDT")

        request.enqueue(object : retrofit2.Callback<AccountBalanceResponse> {
            override fun onResponse(
                call: retrofit2.Call<AccountBalanceResponse>,
                response: Response<AccountBalanceResponse>
            ) {
                Log.d(TAG, "onResponse: $response")
            }

            override fun onFailure(call: retrofit2.Call<AccountBalanceResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: fuck")
            }

        })
    }

    /**
     * UNIX timestamp ISO 8601 rule eg: 2018-02-03T05:34:14.110Z
     */
    private fun getUnixTime(): String {
        return Date(System.currentTimeMillis()).toInstant().toString()
    }
}