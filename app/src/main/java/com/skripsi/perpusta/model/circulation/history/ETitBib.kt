package com.skripsi.perpusta.model.circulation.history


import com.google.gson.annotations.SerializedName

data class ETitBib(
    @SerializedName("ETit")
    val eTit: ETit?,
    @SerializedName("TBBibId")
    val tBBibId: Int?,
    @SerializedName("TBTitId")
    val tBTitId: Int?
)