package com.curve.delivery.response


import com.google.gson.annotations.SerializedName

data class ResetPassResp(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean
) {
    data class Data(
        @SerializedName("accessToken")
        var accessToken: String
    )
}