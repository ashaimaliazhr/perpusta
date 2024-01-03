package com.skripsi.perpusta.model.circulation.status


import com.google.gson.annotations.SerializedName

data class ETitBib(
    @SerializedName("ETit")
    val eTit: ETit?,
    @SerializedName("TBBibId")
    val tBBibId: Int?,
    @SerializedName("TBTitId")
    val tBTitId: Int?
)