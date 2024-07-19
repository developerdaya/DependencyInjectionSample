package com.curve.delivery.response

data class ResponseSignup(
    val success: Boolean,
    val message: String,
    val data: UData
)
data class UData(
    val accessToken: String,
)