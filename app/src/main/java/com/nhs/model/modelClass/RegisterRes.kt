package com.nhs.model.modelClass

import com.google.gson.annotations.SerializedName

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