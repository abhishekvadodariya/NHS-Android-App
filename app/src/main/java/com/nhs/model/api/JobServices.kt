package com.nhs.model.api

import com.nhs.model.modelClass.QuestionChoicesReq
import com.nhs.model.repos.ForgotPasswordRes
import com.nhs.model.repos.QuotationListRes
import com.nhs.model.repos.QuotationSubmitRes
import com.nhs.model.repos.RegisterRes
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

    @POST("save-record?")
    fun postQuotationList(@Query("form_filler_name") form_filler_name:String,
                          @Query("form_filler_phone") form_filler_phone:String,
                          @Query("form_filler_email") form_filler_email:String,
                          @Query("form_filler_address_line_1") form_filler_address_line_1:String,
                          @Query("form_filler_address_line_2") form_filler_address_line_2:String,
                          @Query("form_filler_post_code") form_filler_post_code:String,
                          @Query("form_filler_city") form_filler_city:String,
                          @Query("patient_name") patient_name:String,
                          @Query("patient_date_of_birth") patient_date_of_birth:String,
                          @Query("patient_crn_number") patient_crn_number:String,
                          @Query("patient_nhs_number") patient_nhs_number:String,
                          @Query("form_filler_relationship_with_patient") form_filler_relationship_with_patient:String,
                          @Query("details") details:String,
                          @Query("discuss_permission") discuss_permission:String,
                          @Query("hear_back_permission") hear_back_permission:String,
                          @Query("question_and_choices") question_and_choices:ArrayList<QuestionChoicesReq>): Call<QuotationSubmitRes>
}