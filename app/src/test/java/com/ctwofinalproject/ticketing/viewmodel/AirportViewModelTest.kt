package com.ctwofinalproject.ticketing.viewmodel

import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponseAirport
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Call

class AirportViewModelTest {
    lateinit var service: RestServiceMain

    @Before
    fun setUp(){
        service = mockk()
    }


    @Test
    fun testfetchAirport(): Unit = runBlocking {
        val responseFetchAirport = mockk<Call<ResponseAirport>>()

        every {
            runBlocking {
                service.getAirport()
            }
        } returns responseFetchAirport
        val result = service.getAirport()

        verify {
            runBlocking {
                service.getAirport()
            }
        }
        assertEquals(result,responseFetchAirport)

    }
}