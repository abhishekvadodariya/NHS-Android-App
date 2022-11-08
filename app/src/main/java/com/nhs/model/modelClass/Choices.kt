package com.nhs.model.modelClass

import com.google.gson.annotations.SerializedName

class Choices : BaseResult() {

    @SerializedName("id")
    var id : Int? = null

    @SerializedName("choice")
    var choice : String? = null

    @SerializedName("created_at")
    var createdAt : String? = null

    @SerializedName("updated_at")
    var updatedAt : String? = null

    @SerializedName("deleted_at")
    var deletedAt : String? = null

}