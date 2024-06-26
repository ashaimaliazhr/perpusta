package com.skripsi.perpusta.model.circulation.status

import com.google.gson.annotations.SerializedName

data class Data(
@SerializedName("ID")
val iD: String?,
@SerializedName("ItemNo")
val itemNo: String?,
@SerializedName("ChkODate")
val chkODate: String?,
@SerializedName("DueDate")
val dueDate: String?,
@SerializedName("ChkIDate")
val chkIDate: Any?,
@SerializedName("CItem")
val cItem: CItem?,
)
