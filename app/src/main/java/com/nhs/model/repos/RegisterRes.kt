package com.nhs.model.repos

import com.google.gson.annotations.SerializedName
import com.nhs.model.modelClass.BaseResult

class RegisterRes : BaseResult() {

    @SerializedName("access_token")
    var accessToken: String = ""

    @SerializedName("user")
    var user: User?= User()

    @SerializedName("token_type")
    var tokenType: String = ""

    @SerializedName("expires_in")
    var expiresIn: Int = 0
}