package com.skripsi.perpusta.model.circulation.account


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("CItem")
    val cItem: CItem? = null,

    @SerializedName("ChkODate")
    val chkODate: String? = null,

    @SerializedName("FineAmnt")
    val fineAmnt: Int? = null,

    @SerializedName("ID")
    val iD: String? = null,

    @SerializedName("ItemNo")
    val itemNo: String? = null,

    @SerializedName("PaidAmnt")
    val paidAmnt: Int? = null,

    @SerializedName("PaidDate")
    val paidDate: Any? = null
)