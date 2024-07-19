package com.curve.delivery.response


import com.google.gson.annotations.SerializedName

data class UploadImageResp(
    @SerializedName("data")
    var `data`: String,
    @SerializedName("success")
    var success: Boolean
)