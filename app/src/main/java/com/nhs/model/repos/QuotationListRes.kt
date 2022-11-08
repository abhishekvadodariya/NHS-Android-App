package com.nhs.model.repos

import com.google.gson.annotations.SerializedName
import com.nhs.model.modelClass.BaseResult

class QuotationListRes: BaseResult() {
    @SerializedName("status")
    var status : String? = null

    @SerializedName("data")
    var data : ArrayList<Data> = arrayListOf()
}