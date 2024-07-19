package com.curve.delivery.model


import com.google.gson.annotations.SerializedName

data class AddBankRequest(
    var accHolderName: String,
    var bankAccountNo: String,
    var bankCode: String,
    var bankName: String
)