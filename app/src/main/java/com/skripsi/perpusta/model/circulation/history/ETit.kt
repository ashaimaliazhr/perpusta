package com.skripsi.perpusta.model.circulation.history


import com.google.gson.annotations.SerializedName

data class ETit(
    @SerializedName("TitId")
    val titId: Int?,
    @SerializedName("TitKey")
    val titKey: String?
)