package com.ctwofinalproject.ticketing.api

import android.app.Application
import com.ctwofinalproject.ticketing.daos.AirportDAO
import com.ctwofinalproject.ticketing.daos.RecentSearchDAO
import com.ctwofinalproject.ticketing.db.TicketingDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiConfig {
    const val BASE_URL_PROVINCE= "https://irvanwijayasardam.github.io/api-wilayah-indonesia/"
    const val BASE_URL_MAIN= "https://platinum-project-backend-production.up.railway.app/v1/"

    private  val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder().connectTimeout(2,TimeUnit.MINUTES).addInterceptor(logging).build()

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

    @Singleton
    @Provides
    fun createDB(context: Application): TicketingDB =
        TicketingDB.getDatabase(context)

    @Singleton
    @Provides
    fun getSourceDao(ticketingDB: TicketingDB): AirportDAO =
        ticketingDB.airportDao()

    @Singleton
    @Provides
    fun getRecentSearchDao(ticketingDB: TicketingDB): RecentSearchDAO =
        ticketingDB.recentSearchDao()

}