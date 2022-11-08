package com.nhs.model.repos

import com.google.gson.annotations.SerializedName
import com.nhs.model.modelClass.BaseResult

class User: BaseResult() {

    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("role_id")
    var role_id: Int = 0

    @SerializedName("name")
    var name: String = ""

    @SerializedName("email")
    var email: String = ""

    @SerializedName("avatar")
    var avatar: String = ""

    @SerializedName("email_verified_at")
    var email_verified_at: String = ""

    @SerializedName("settings")
    var settings: ArrayList<String> = arrayListOf()

    @SerializedName("created_at")
    var createdAt: String? = ""

    @SerializedName("updated_at")
    var updatedAt: String? = ""

    @SerializedName("username")
    var username: String? = ""

    @SerializedName("stripe_id")
    var stripeId: String? = ""

    @SerializedName("card_brand")
    var cardBrand: String? = ""

    @SerializedName("card_last_four")
    var cardLastFour: String? = ""

    @SerializedName("trial_ends_at")
    var trialEndsAt: String? = ""

    @SerializedName("verification_code")
    var verificationCode: String? = ""

    @SerializedName("verified")
    var verified: String? = ""
}