package com.nhs.model.repos

import com.google.gson.annotations.SerializedName
import com.nhs.model.modelClass.BaseResult

class ForgotPasswordRes: BaseResult() {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("data")
    var data: String = ""
}