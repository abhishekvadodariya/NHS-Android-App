package com.nhs.model.repos

import com.google.gson.annotations.SerializedName
import com.nhs.model.modelClass.BaseResult

class QuotationSubmitRes:BaseResult() {

    @SerializedName("status")
    var status  : String? = null

    @SerializedName("message")
    var message : String? = null

    @SerializedName("data")
    var data: QuotationSubmitData = QuotationSubmitData()

}