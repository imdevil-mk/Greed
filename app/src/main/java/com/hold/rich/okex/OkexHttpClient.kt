package com.hold.rich.okex

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkexHttpClient {

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    var client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor(OkexHeadersInterceptor())
        .build()
        private set
}