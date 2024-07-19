package com.curve.delivery.new_architecture.repository

import android.content.Context
import com.curve.delivery.model.AddBankRequest
import com.curve.delivery.model.AddVehicleRequest
import com.curve.delivery.model.LoginRequest
import com.curve.delivery.model.NewPasswordRequest
import com.curve.delivery.model.OTPVerifyRequest
import com.curve.delivery.model.RegisterRequest
import com.curve.delivery.model.ResetPassRequest
import com.curve.delivery.new_architecture.helper.EmpBaseRepository
import com.curve.delivery.response.NewPasswordResp
import com.curve.delivery.response.ResetPassResp
import com.curve.delivery.response.UploadImageResp
import com.curve.delivery.util.ApiInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import javax.inject.Inject

class AuthRepository @Inject constructor(var apiService:ApiInterface, @ApplicationContext context: Context) :EmpBaseRepository() {

    suspend fun hitLogin(model: LoginRequest) = safeApiCall {
        apiService.hitLogin(request = model)
    }

    suspend fun hitRegister(model: RegisterRequest)=safeApiCall {
        apiService.hitRegister1(request = model)
    }

    suspend fun hitVerifyOtp(accessToken: String, model: OTPVerifyRequest)=safeApiCall {
        apiService.hitVerifyOtp1(accessToken=accessToken, request =model)
    }

    suspend fun hitAddBank(accessToken: String, model: AddBankRequest)=safeApiCall {
        apiService.hitAddBank(accessToken, model)
    }

    suspend fun hitAddVehicle(accessToken: String,
        model: AddVehicleRequest)=safeApiCall {
        apiService.hitAddVehicle(accessToken = accessToken, request = model)
    }

    suspend fun hitResetPassword(accessToken: String,
       model: ResetPassRequest)=safeApiCall {
        apiService.hitResetPassword(accessToken = accessToken, request = model)
    }

    suspend fun hitNewPassword(accessToken: String,
        model: NewPasswordRequest)=safeApiCall {
        apiService.hitNewPassword(accessToken=accessToken, request = model)
    }

    suspend fun uploadFile(upload_delivery_file: MultipartBody.Part? = null)=safeApiCall {
        apiService.uploadFile(upload_delivery_file)
    }
}