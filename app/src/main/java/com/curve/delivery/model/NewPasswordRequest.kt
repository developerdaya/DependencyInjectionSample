package com.curve.delivery.model

import com.google.gson.annotations.SerializedName
data class NewPasswordRequest(
    var currentPassword: String,
    var password: String
)