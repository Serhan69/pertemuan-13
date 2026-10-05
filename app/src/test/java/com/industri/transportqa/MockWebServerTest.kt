package com.industri.transportqa

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FlightApiService {
    @GET("api/v1/flights/availabilities")
    suspend fun getAvailabilities(): List<String>
}

class MockWebServerTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: FlightApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(FlightApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getAvailabilities_successResponse_returnsFlightList() = runBlocking {
        val jsonResponse = """
            [
              "GA-102 (CGK - SUB)",
              "GA-204 (CGK - DPS)"
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
        )

        val flights = apiService.getAvailabilities()

        assertThat(flights).hasSize(2)
        assertThat(flights[0]).contains("CGK - SUB")
    }
}
