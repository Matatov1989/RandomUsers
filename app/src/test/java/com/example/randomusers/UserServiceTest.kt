package com.example.randomusers

import com.example.randomusers.network.RandomUserApi
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserServiceTest {

    private lateinit var api: RandomUserApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetUserData() {
        val mockUserJson = """{"id":"1","name":"John Doe","email":"johndoe@example.com"}"""
        mockWebServer.enqueue(
            MockResponse()
            .setBody(mockUserJson)
            .addHeader("Content-Type", "application/json"))

        val response = api.getUserDataTest().execute()

        assertEquals(true, response.isSuccessful)
    }
}