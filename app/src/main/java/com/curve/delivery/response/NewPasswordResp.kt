package com.curve.delivery.response


import com.google.gson.annotations.SerializedName

data class NewPasswordResp(
    @SerializedName("message")
    var message: String
)