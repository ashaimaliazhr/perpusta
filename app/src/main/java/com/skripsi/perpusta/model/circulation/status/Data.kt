package com.skripsi.perpusta.model.circulation.status


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("CItem")
    val cItem: CItem?,
    @SerializedName("ChkIDate")
    val chkIDate: Any?,
    @SerializedName("ChkODate")
    val chkODate: String?,
    @SerializedName("DueDate")
    val dueDate: String?,
    @SerializedName("ID")
    val iD: String?,
    @SerializedName("ItemNo")
    val itemNo: String?
)