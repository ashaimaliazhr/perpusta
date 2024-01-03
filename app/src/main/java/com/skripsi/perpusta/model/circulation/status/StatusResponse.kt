package com.skripsi.perpusta.model.circulation.status


import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("length")
    val length: Int?,
    @SerializedName("message")
    val message: String?
)