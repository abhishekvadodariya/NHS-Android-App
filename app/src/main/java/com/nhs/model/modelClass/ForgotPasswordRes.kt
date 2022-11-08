package com.nhs.model.modelClass

import com.google.gson.annotations.SerializedName

class ForgotPasswordRes:BaseResult() {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("data")
    var data: String = ""
}