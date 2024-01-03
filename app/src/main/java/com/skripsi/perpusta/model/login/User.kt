package com.skripsi.perpusta.model.login

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("Addr")
    val addr: String?,
    @SerializedName("EMail")
    val eMail: String?,
    @SerializedName("FName")
    val fName: String?,
    @SerializedName("ID")
    val iD: String?,
    @SerializedName("LName")
    val lName: String?,
    @SerializedName("Phone")
    val phone: String?,
    @SerializedName("Pwd")
    val pwd: String?
)
