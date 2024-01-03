package com.skripsi.perpusta.model.circulation.history


import com.google.gson.annotations.SerializedName
import com.skripsi.perpusta.model.circulation.history.EIdn

data class EBib(
    @SerializedName("BibId")
    val bibId: Int?,
    @SerializedName("CalKey")
    val calKey: String?,
    @SerializedName("EIdn")
    val eIdn: EIdn?,
    @SerializedName("EdiRaw")
    val ediRaw: String?,
    @SerializedName("PubRaw")
    val pubRaw: String?
)