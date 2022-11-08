package com.nhs.model.repos

import com.google.gson.annotations.SerializedName
import com.nhs.model.modelClass.BaseResult

class QuotationSubmitData :BaseResult(){

    @SerializedName("form_filler_name")
    var formFillerName:String? = null

    @SerializedName("form_filler_phone")
    var formFillerPhone: String? = null

    @SerializedName("form_filler_email")
    var formFillerEmail: String? = null

    @SerializedName("form_filler_address_line_1")
    var formFillerAddressLine1: String? = null

    @SerializedName("form_filler_address_line_2")
    var formFillerAddressLine2: String? = null

    @SerializedName("form_filler_post_code")
    var formFillerPostCode: String? = null

    @SerializedName("form_filler_city")
    var formFillerCity: String? = null

    @SerializedName("form_filler_user_id")
    var formFillerUserId: Int?    = null

    @SerializedName("patient_name")
    var patientName: String? = null

    @SerializedName("patient_date_of_birth")
    var patientDateOfBirth : String? = null

    @SerializedName("patient_crn_number")
    var patientCrnNumber: String? = null

    @SerializedName("patient_nhs_number")
    var patientNhsNumber: String? = null

    @SerializedName("form_filler_relationship_with_patient")
    var formFillerRelationshipWithPatient : String? = null

    @SerializedName("details")
    var details: String? = null

    @SerializedName("discuss_permission")
    var discussPermission: String? = null

    @SerializedName("hear_back_permission")
    var hearBackPermission: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("id")
    var id: Int?    = null

}