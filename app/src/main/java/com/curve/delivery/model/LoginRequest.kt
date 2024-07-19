package com.curve.delivery.model
import com.google.gson.annotations.SerializedName


data class LoginRequest(
    var countryCode: String,
    var mobileNumber: String,
    var lat: Double,
    var long: Double,
    var password: String
)