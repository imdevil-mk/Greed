package com.hold.rich.di

import com.hold.rich.ApiResponseCallAdapterFactory
import com.hold.rich.ApiResponseConverterFactory
import com.hold.rich.okex.OkexConfig
import com.hold.rich.okex.OkexHeadersInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(OkexHeadersInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkexRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(OkexConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ApiResponseConverterFactory)
        .addCallAdapterFactory(ApiResponseCallAdapterFactory())
        .build()
}