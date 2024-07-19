package com.curve.delivery.model
import com.google.gson.annotations.SerializedName
/* "name":"jeet",
    profileImage:"",
    "countryCode":"+91",
    "email": "",
    "language": "en",      // english->en and arabic-> ar
    "dob": "1716207822238",
    "mobileNumber":"7535900353",
    "deviceToken":"",
    deviceType:1    // 1 for android 2 ios 3 web
    "lat":22.77,
    "long":77.22,
    "isSmoke": 0,        //  0: no smoke   and 1: smoke
    "gender": 0,
    "password":"jeet",
    "idProofFront": "",
    "idProofBack": "",
    "policeBackground": ""*/

data class RegisterRequest(
    var name: String,
    var profileImage: String,
    var countryCode: String,
    var email: String,
    var language: String,
    var dob: String,
    var mobileNumber: String,
    var deviceToken: String,
    var deviceType: Int,
    var lat: Double,
    var long: Double,
    var isSmoke: Int,
    var gender: Int,
    var password: String,
    var idProofFront: String,
    var idProofBack: String)