package com.ctwofinalproject.ticketing.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiConfig {
    const val BASE_URL_PROVINCE= "https://irvanwijayasardam.github.io/api-wilayah-indonesia/"
    const val BASE_URL_MAIN= "https://mynotesapp-production-95d8.up.railway.app"

    private  val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    @Singleton
    @Provides
    fun provideProvince() : RestServiceProvince =
        Retrofit.Builder()
            .baseUrl(BASE_URL_PROVINCE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RestServiceProvince::class.java)

    @Singleton
    @Provides
    fun provideMain() : RestServiceMain =
        Retrofit.Builder()
            .baseUrl(BASE_URL_MAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RestServiceMain::class.java)

}