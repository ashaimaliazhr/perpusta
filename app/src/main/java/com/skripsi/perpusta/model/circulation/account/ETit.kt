package com.skripsi.perpusta.model.circulation.account


import com.google.gson.annotations.SerializedName

data class ETit(
    @SerializedName("TitId")
    val titId: Int?,
    @SerializedName("TitKey")
    val titKey: String?
)