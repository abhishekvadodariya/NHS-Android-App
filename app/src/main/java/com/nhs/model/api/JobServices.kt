package com.nhs.model.api

import com.nhs.model.modelClass.ForgotPasswordRes
import com.nhs.model.modelClass.QuotationListRes
import com.nhs.model.modelClass.RegisterRes
import retrofit2.Call
import retrofit2.http.*

interface JobServices {

    @POST("register?")
    fun registerUser(@Query("name") name:String,@Query("email")email:String,@Query("password")password:String,@Query("password_confirmation")confirmPassword:String): Call<RegisterRes>

    @POST("login?")
    fun userLogin(@Query("email") name:String,@Query("password")email:String): Call<RegisterRes>

    @POST("send-password-reset-link?")
    fun userForgotPassword(@Query("email") email:String): Call<ForgotPasswordRes>

    @GET("load-questions-with-choices")
    fun getQuotationList(@Header("Authorization") token:String): Call<QuotationListRes>
}