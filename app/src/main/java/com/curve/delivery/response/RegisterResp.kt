package com.curve.delivery.response
import com.google.gson.annotations.SerializedName


data class RegisterResp(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean
) {
    data class Data(
        @SerializedName("accessToken")
        var accessToken: String,
        @SerializedName("countryCode")
        var countryCode: String,
        @SerializedName("mobileNumber")
        var mobileNumber: String
    )
}