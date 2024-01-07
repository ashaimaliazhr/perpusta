package com.skripsi.perpusta.model.circulation.account


import com.google.gson.annotations.SerializedName

data class ETitBib(
    @SerializedName("ETit")
    val eTit: ETit? = null,

    @SerializedName("TBBibId")
    val tBBibId: Int? = null,

    @SerializedName("TBTitId")
    val tBTitId: Int? = null
)