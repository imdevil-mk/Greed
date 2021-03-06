package com.hold.rich.di

import com.hold.rich.api.ApiResponseCallAdapterFactory
import com.hold.rich.api.ApiResponseConverterFactory
import com.hold.rich.api.manager.AccountManager
import com.hold.rich.api.manager.AssetManager
import com.hold.rich.api.manager.TradeManager
import com.hold.rich.api.service.AccountService
import com.hold.rich.api.service.AssetService
import com.hold.rich.api.service.TradeService
import com.hold.rich.okex.BASE_URL
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
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ApiResponseConverterFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory())
        .build()


    @Singleton
    @Provides
    fun provideAccountService(retrofit: Retrofit): AccountService =
        retrofit.create(AccountService::class.java)

    @Singleton
    @Provides
    fun provideAccountManager(service: AccountService): AccountManager = AccountManager(service)

    @Singleton
    @Provides
    fun provideAssetService(retrofit: Retrofit): AssetService =
        retrofit.create(AssetService::class.java)

    @Singleton
    @Provides
    fun provideAssetManager(service: AssetService): AssetManager = AssetManager(service)

    @Singleton
    @Provides
    fun provideTradeService(retrofit: Retrofit): TradeService =
        retrofit.create(TradeService::class.java)

    @Singleton
    @Provides
    fun provideTradeManager(service: TradeService): TradeManager = TradeManager(service)
}