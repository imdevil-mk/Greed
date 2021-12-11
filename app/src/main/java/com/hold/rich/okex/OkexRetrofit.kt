package com.hold.rich.okex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OkexRetrofit {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(OkexConfig.BASE_URL)
        .client(OkexHttpClient.client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        private set
}