package com.curve.delivery.response


import com.google.gson.annotations.SerializedName

data class AddVehicleResp(
    @SerializedName("message")
    var message: String?
)