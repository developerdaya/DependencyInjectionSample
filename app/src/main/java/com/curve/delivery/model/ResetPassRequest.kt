package com.curve.delivery.model


import com.google.gson.annotations.SerializedName

data class ResetPassRequest(
    var countryCode: String,
    var mobileNumber: String
)