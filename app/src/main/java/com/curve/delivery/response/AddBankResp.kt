package com.curve.delivery.response


import com.google.gson.annotations.SerializedName

data class AddBankResp(
    @SerializedName("auth")
    var auth: Boolean,
    @SerializedName("message")
    var message: String
)