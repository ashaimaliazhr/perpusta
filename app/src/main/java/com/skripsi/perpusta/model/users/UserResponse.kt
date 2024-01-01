package com.skripsi.perpusta.model.users


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?
)