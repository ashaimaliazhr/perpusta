package com.skripsi.perpusta.model.circulation.history


import com.google.gson.annotations.SerializedName

data class EIdn(
    @SerializedName("IdnBibId")
    val idnBibId: Int?,
    @SerializedName("IdnId")
    val idnId: Int?,
    @SerializedName("IdnKey")
    val idnKey: String?
)