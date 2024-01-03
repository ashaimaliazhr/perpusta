package com.skripsi.perpusta.model.circulation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CirculationRequest (
    @SerializedName("npm")
    @Expose
    var npm : String? = null,
)