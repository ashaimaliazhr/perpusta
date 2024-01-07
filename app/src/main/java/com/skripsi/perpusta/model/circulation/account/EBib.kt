package com.skripsi.perpusta.model.circulation.account


import com.google.gson.annotations.SerializedName

data class EBib(
    @SerializedName("BibId")
    val bibId: Int? = null,

    @SerializedName("CalKey")
    val calKey: String? = null,

    @SerializedName("EdiRaw")
    val ediRaw: String? = null,

    @SerializedName("PubRaw")
    val pubRaw: String? = null
)