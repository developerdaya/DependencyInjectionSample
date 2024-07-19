package com.curve.delivery.model
import com.google.gson.annotations.SerializedName


data class AddVehicleRequest(
    var vechileName: String,
    var certificate: String,
    var drivingLicenseFront: String,
    var drivingLicenseBack: String)