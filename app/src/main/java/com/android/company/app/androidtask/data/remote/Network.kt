package com.android.company.app.androidtask.data.remote

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.Http2
import okhttp3.internal.http2.Http2Connection
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.HTTP


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
        client.addInterceptor(MockInterceptor)
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


    object MockInterceptor : Interceptor {
        private const val SUCCESS_CODE = 200
        override fun intercept(chain: Interceptor.Chain): Response {

            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith("foods") -> getListOfFoodsJson
                else -> ""
            }
            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
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

private const val getListOfFoodsJson = """{
        "success": true,
        "data": [
        {
        "id" : 1,
        "name" : "Cheese",
        "price" : 25.2,
        "url" : "https://images.pexels.com/photos/4109950/pexels-photo-4109950.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

        },
        {
        "id" : 2,
        "name" : "Milk",
        "price" : 10.2,
        "url" : "https://images.pexels.com/photos/248412/pexels-photo-248412.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 3,
        "name" : "Egg",
        "price" : 1.50,
        "url" : "https://upload.wikimedia.org/wikipedia/en/thumb/5/58/Instagram_egg.jpg/220px-Instagram_egg.jpg"

        },
        {
        "id" : 4,
        "name" : "Tomato",
        "price" : 12.50,
        "url" : "https://media.istockphoto.com/photos/tomato-isolated-on-white-background-picture-id466175630?k=6&m=466175630&s=612x612&w=0&h=fu_mQBjGJZIliOWwCR0Vf2myRvKWyQDsymxEIi8tZ38="

        },
        {
        "id" : 5,
        "name" : "Potato",
        "price" : 3.80,
        "url" : "https://images.pexels.com/photos/2286776/pexels-photo-2286776.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 6,
        "name" : "Orange",
        "price" : 7.50,
        "url" : "https://images.pexels.com/photos/42059/citrus-diet-food-fresh-42059.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

        },
        {
        "id" : 7,
        "name" : "Mango",
        "price" : 17.50,
        "url" : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlYU0nszG22I7201oge4_WcrPAUT0KCx923Q&usqp=CAU"

        },
        {
        "id" : 8,
        "name" : "Banana",
        "price" : 6.50,
        "url" : "https://api.time.com/wp-content/uploads/2019/11/gettyimages-459761948.jpg?quality=85&w=1024&h=512&crop=1"

        },
        {
        "id" : 9,
        "name" : "Strawberry",
        "price" : 11.50,
        "url" : "https://images.pexels.com/photos/934066/pexels-photo-934066.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 10,
        "name" : "Pepsi",
        "price" : 5.50,
        "url" : "https://images.pexels.com/photos/1292294/pexels-photo-1292294.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 11,
        "name" : "Coca Cola",
        "price" : 5.50,
        "url" : "https://images.pexels.com/photos/50593/coca-cola-cold-drink-soft-drink-coke-50593.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 12,
        "name" : "Water",
        "price" : 2.50,
        "url" : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmjhDKL00g8gHZVzhtHRrkvGS2ltv727R_NA&usqp=CAU"

        },
        {
        "id" : 13,
        "name" : "Watermelon",
        "price" : 8.50,
        "url" : "https://images.pexels.com/photos/260426/pexels-photo-260426.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 14,
        "name" : "Apple",
        "price" : 18.00,
        "url" : "https://images.pexels.com/photos/102104/pexels-photo-102104.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 15,
        "name" : "Grape",
        "price" : 7.00,
        "url" : "https://images.ctfassets.net/cnu0m8re1exe/6uSVPiUx1FloQ23j38x2aM/0eafe5c0d6b3ce7e3aae6b389a997423/Grapes.jpg?w=650&h=433&fit=fill"

        },
        {
        "id" : 16,
        "name" : "Apricot",
        "price" : 5.50,
        "url" : "https://images.pexels.com/photos/1268122/pexels-photo-1268122.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        },
        {
        "id" : 17,
        "name" : "Plum",
        "price" : 5.50,
        "url" : "https://images.pexels.com/photos/248440/pexels-photo-248440.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        },
        {
        "id" : 18,
        "name" : "Carrots",
        "price" : 5.50,
        "url" : "https://solidstarts.com/wp-content/uploads/Carrot-Edited-480x320.jpg"

        },
        {
        "id" : 19,
        "name" : "Cucumber",
        "price" : 2.50,
        "url" : "https://images.pexels.com/photos/2329440/pexels-photo-2329440.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        },
        {
        "id" : 20,
        "name" : "Meat",
        "price" : 140.50,
        "url" : "https://www.foodbusinessnews.net/ext/resources/2019/12/MeatPoultryProducts_Lead.jpg?1576769837"
        },
        {
        "id" : 21,
        "name" : "Avocado",
        "price" : 14.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/avocado_small.jpg"
        },
        {
        "id" : 22,
        "name" : "Blackberries",
        "price" : 14.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2019/03/BlackberrySmall.jpg"
        },
        {
        "id" : 23,
        "name" : "Cantaloupe",
        "price" : 6.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/cantaloupe_small.gif"
        },
        {
        "id" : 24,
        "name" : "Cherries",
        "price" : 15.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/cherries_small.jpg"
        },
        {
        "id" : 25,
        "name" : "Clementine",
        "price" : 15.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/clementine_small.gif"
        },
        {
        "id" : 26,
        "name" : "Coconut Meat",
        "price" : 12.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/coconutmeat_small.gif"
        },
        {
        "id" : 27,
        "name" : "Guava",
        "price" : 12.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/guava_small.jpg"
        },
        {
        "id" : 28,
        "name" : "Lemon",
        "price" : 12.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/lemon_small.gif"
        },
        {
        "id" : 29,
        "name" : "Pear",
        "price" : 12.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/pear_small.jpg"
        },
        {
        "id" : 30,
        "name" : "Pineapple",
        "price" : 12.50,
        "url" : "https://halfyourplate-4kgxi1gvwldjzby.stackpathdns.com/wp-content/uploads/2015/01/pineapple_small.jpg"
        }
        ]
}"""

