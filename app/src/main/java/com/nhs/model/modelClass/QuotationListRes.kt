package com.nhs.model.modelClass

import com.google.gson.annotations.SerializedName

class QuotationListRes:BaseResult() {
    @SerializedName("status")
    var status : String? = null

    @SerializedName("data")
    var data : ArrayList<Data> = arrayListOf()
}