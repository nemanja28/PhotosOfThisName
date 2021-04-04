package com.ns.photosofthisname


import com.ns.photosofthisname.datalayer.RemoteRestDataService
import com.ns.photosofthisname.di.configureTestAppComponent
import com.ns.photosofthisname.model.APIError
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File
import java.net.HttpURLConnection



@RunWith(JUnit4::class)
class SearchPhotosTest : KoinTest {
    private lateinit var mockServerInstance: MockWebServer

    @Before
    fun start() {
        startMockServer()
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @KoinApiExtension
    @Test
    fun test_search_photos_success_response() = runBlocking<Unit> {
        mockNetworkResponseWithFileContent(HttpURLConnection.HTTP_OK, SUCCESS_RESPONSE_SEARCH_PHOTOS_FILE)
        val dataService = RemoteRestDataService()
        val result = dataService.searchPhotoByName(SEARCH_TERM)

        assert(result.isSuccess)
        result.onSuccess {
            assertEquals(it.photos.photo.size, PHOTO_LIST_SIZE)
        }
    }

    @KoinApiExtension
    @Test
    fun test_search_photos_failed_response() = runBlocking<Unit> {
        mockNetworkResponseWithFileContent(HttpURLConnection.HTTP_BAD_REQUEST)
        val dataService = RemoteRestDataService()
        val result = dataService.searchPhotoByName(SEARCH_TERM)

        assert(result.isFailure)
        result.onFailure {
            assertThat(it, instanceOf(APIError::class.java))
            val apiError = it as APIError
            assertEquals(apiError.code, HttpURLConnection.HTTP_BAD_REQUEST)
        }
    }

    private fun mockNetworkResponseWithFileContent(responseCode: Int, fileName: String? = null) {
        val mockResponse = MockResponse().setResponseCode(responseCode)
        fileName?.let {
            mockResponse.setBody(getJson(it))
        }
        return mockServerInstance.enqueue(mockResponse)
    }

    private fun getJson(path: String): String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    private fun getMockWebServerUrl() = mockServerInstance.url("/").toString()

    private fun startMockServer() {
        mockServerInstance = MockWebServer()
        mockServerInstance.start()
    }

    private fun stopMockServer() = mockServerInstance.shutdown()

    @After
    fun tearDown() {
        stopMockServer()
        stopKoin()
    }

    companion object {
        private const val SUCCESS_RESPONSE_SEARCH_PHOTOS_FILE = "success_response_search_photos.json"
        private const val SEARCH_TERM = "test"
        private const val PHOTO_LIST_SIZE = 20

    }
}