package com.curve.delivery.response
import com.google.gson.annotations.SerializedName


data class LoginResp(
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
        @SerializedName("addressDetails")
        var addressDetails: AddressDetails,
        @SerializedName("bankDetails")
        var bankDetails: BankDetails,
        @SerializedName("countryCode")
        var countryCode: String,
        @SerializedName("createdAt")
        var createdAt: Long,
        @SerializedName("deviceToken")
        var deviceToken: String,
        @SerializedName("deviceType")
        var deviceType: Int,
        @SerializedName("dob")
        var dob: String,
        @SerializedName("documents")
        var documents: Documents,
        @SerializedName("email")
        var email: String,
        @SerializedName("fullyVerify")
        var fullyVerify: Int,
        @SerializedName("gender")
        var gender: Int,
        @SerializedName("_id")
        var id: String,
        @SerializedName("isBankDetailsUpdated")
        var isBankDetailsUpdated: Boolean,
        @SerializedName("isDelivery")
        var isDelivery: Boolean,
        @SerializedName("isDocumentsUploaded")
        var isDocumentsUploaded: Boolean,
        @SerializedName("isLocationDetails")
        var isLocationDetails: Boolean,
        @SerializedName("isOtpVerified")
        var isOtpVerified: Boolean,
        @SerializedName("isBlocked ")
        var isBlocked : Boolean,
        @SerializedName("isPickUp")
        var isPickUp: Boolean,
        @SerializedName("isSmoke")
        var isSmoke: Int,
        @SerializedName("isVechileDocUploaded")
        var isVechileDocUploaded: Boolean,
        @SerializedName("language")
        var language: String,
        @SerializedName("location")
        var location: List<Double>,
        @SerializedName("mobileNumber")
        var mobileNumber: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("otp")
        var otp: Int,
        @SerializedName("password")
        var password: String,
        @SerializedName("profileImage")
        var profileImage: String,
        @SerializedName("status")
        var status: String,
        @SerializedName("updatedAt")
        var updatedAt: Long,
        @SerializedName("vechileDetails")
        var vechileDetails: VechileDetails
    ) {
        data class AddressDetails(
            @SerializedName("address")
            var address: String,
            @SerializedName("building")
            var building: String,
            @SerializedName("postalCode")
            var postalCode: String,
            @SerializedName("street")
            var street: String
        )

        data class BankDetails(
            @SerializedName("accHolderName")
            var accHolderName: String,
            @SerializedName("bankAccountNo")
            var bankAccountNo: String,
            @SerializedName("bankCode")
            var bankCode: String,
            @SerializedName("bankName")
            var bankName: String
        )

        data class Documents(
            @SerializedName("idProofBack")
            var idProofBack: String,
            @SerializedName("idProofFront")
            var idProofFront: String,
            @SerializedName("policeBackground")
            var policeBackground: String
        )

        data class VechileDetails(
            @SerializedName("certificate")
            var certificate: String,
            @SerializedName("drivingLicenseBack")
            var drivingLicenseBack: String,
            @SerializedName("drivingLicenseFront")
            var drivingLicenseFront: String,
            @SerializedName("vechileName")
            var vechileName: String
        )
    }
}