package com.nhs.model.api

import com.example.firozhasan.retrofitkotlinexample.`interface`.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JobAPI {

    object JobAPI {

        //private var retrofit: Retrofit? = null
        private var client = OkHttpClient.Builder().build()

        //val client: Retrofit
        private val retrofit = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(Constant.BASE_URL)
                            .client(client)
                            .build()

        fun<T> buildService(service: Class<T>): T?{
            return retrofit.create(service)
        }

        /*fun getInstance(): Retrofit {
            var mHttpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            var mOkHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .build()


            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build()
            return retrofit
        }*/

    }
}