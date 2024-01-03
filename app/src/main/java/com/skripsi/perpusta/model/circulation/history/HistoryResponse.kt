package com.skripsi.perpusta.model.circulation.history


import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("length")
    val length: Int?,
    @SerializedName("message")
    val message: String?
)