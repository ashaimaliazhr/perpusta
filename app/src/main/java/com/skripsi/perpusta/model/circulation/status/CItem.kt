package com.skripsi.perpusta.model.circulation.status


import com.google.gson.annotations.SerializedName

data class CItem(
    @SerializedName("CopyNo")
    val copyNo: Int?,
    @SerializedName("EBib")
    val eBib: EBib?,
    @SerializedName("ETitBib")
    val eTitBib: ETitBib?,
    @SerializedName("ItemBib")
    val itemBib: Int?,
    @SerializedName("ItemClss")
    val itemClss: String?,
    @SerializedName("ItemNo")
    val itemNo: String?,
    @SerializedName("LocaCode")
    val locaCode: String?
)