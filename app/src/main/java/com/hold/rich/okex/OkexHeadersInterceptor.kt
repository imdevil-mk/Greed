package com.hold.rich.okex

import android.util.Log
import com.hold.rich.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

private const val TAG = "OkexHeadersInterceptor"

class OkexHeadersInterceptor : Interceptor {

    private val apiKey = BuildConfig.apiKey
    private val secretKey = BuildConfig.secretKey
    private val passPhrase = BuildConfig.passPhrase

    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()

        val timestamp = getUnixTime()
        val method = origin.method
        val requestPath = origin.url.toString().replace(OkexConfig.BASE_URL, "")
        val sign = HmacSHA256Base64Utils.sign(timestamp, method, requestPath, null, null, secretKey)

        Log.d(TAG, "intercept: $method")
        Log.d(TAG, "intercept: $requestPath")

        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("OK-ACCESS-KEY", apiKey)
            .addHeader("OK-ACCESS-SIGN", sign)
            .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
            .addHeader("OK-ACCESS-PASSPHRASE", passPhrase)
            .build()
        return chain.proceed(request)
    }

    /**
     * UNIX timestamp ISO 8601 rule eg: 2018-02-03T05:34:14.110Z
     */
    private fun getUnixTime(): String {
        return Date(System.currentTimeMillis()).toInstant().toString()
    }
}