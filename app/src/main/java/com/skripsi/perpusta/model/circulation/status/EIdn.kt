package com.skripsi.perpusta.model.circulation.status


import com.google.gson.annotations.SerializedName

data class EIdn(
    @SerializedName("IdnBibId")
    val idnBibId: Int?,
    @SerializedName("IdnId")
    val idnId: Int?,
    @SerializedName("IdnKey")
    val idnKey: String?
)