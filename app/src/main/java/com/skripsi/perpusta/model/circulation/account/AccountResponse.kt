package com.skripsi.perpusta.model.circulation.account


import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("length")
    val length: Int?,
    @SerializedName("message")
    val message: String?
)