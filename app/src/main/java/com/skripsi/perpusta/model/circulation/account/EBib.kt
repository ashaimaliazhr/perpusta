package com.skripsi.perpusta.model.circulation.account


import com.google.gson.annotations.SerializedName

data class EBib(
    @SerializedName("BibId")
    val bibId: Int?,
    @SerializedName("CalKey")
    val calKey: String?,
    @SerializedName("EdiRaw")
    val ediRaw: String?,
    @SerializedName("PubRaw")
    val pubRaw: String?
)