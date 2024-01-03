package com.skripsi.perpusta.model.circulation.account


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("CItem")
    val cItem: CItem?,
    @SerializedName("ChkODate")
    val chkODate: String?,
    @SerializedName("FineAmnt")
    val fineAmnt: Int?,
    @SerializedName("ID")
    val iD: String?,
    @SerializedName("ItemNo")
    val itemNo: String?,
    @SerializedName("PaidAmnt")
    val paidAmnt: Int?,
    @SerializedName("PaidDate")
    val paidDate: Any?
)