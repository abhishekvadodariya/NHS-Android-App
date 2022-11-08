package com.nhs.model.repos

import com.google.gson.annotations.SerializedName
import com.nhs.model.modelClass.BaseResult

class Data : BaseResult() {

    @SerializedName("id")
    var id : Int? = null

    @SerializedName("question")
    var question : String? = null

    @SerializedName("required")
    var required : Int? = null

    @SerializedName("created_at")
    var createdAt : String? = null

    @SerializedName("updated_at")
    var updatedAt : String? = null

    @SerializedName("deleted_at")
    var deletedAt : String? = null

    @SerializedName("order")
    var order : Int? = null
    @SerializedName("choices")
    var choices : ArrayList<Choices> = arrayListOf()

}