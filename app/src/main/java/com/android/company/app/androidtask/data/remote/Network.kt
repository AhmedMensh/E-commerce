package com.android.company.app.androidtask.data.remote

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object Network {

    const val REQUIRE_AUTHENTICATION = "Require-Authentication"
    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
    lateinit var retrofit: Retrofit

    var authToken: String? = null

    fun init(baseUrl: String, isDebug: Boolean = false) {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(buildClient(isDebug))
            .build()
    }

    private fun buildClient(isDebug: Boolean): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.addInterceptor(ApiInterceptor)
        if (isDebug) {
            client.addInterceptor(logging)
        }
        return client.build()
    }


    object ApiInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()

            request = if (request.header(REQUIRE_AUTHENTICATION) != null) {
                request.newBuilder()
                    .removeHeader(REQUIRE_AUTHENTICATION)
                    .addHeader("Authorization", "Bearer $authToken")
                    .addHeader("Accept", "application/json")
                    .build()
            } else {
                request.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
            }

            return chain.proceed(request)
        }

    }

    private const val getListOfFoodsJson = """"{
        "success": true,
        "data": [],
        "currentPage": 1,
        "totalPages": 0
    }""""


    object MockInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val uri = chain.request().url.toUri().toString()
            val responseString = getListOfFoodsJson
            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        }

    }
}
