package com.curve.delivery.util
import com.curve.delivery.model.AddBankRequest
import com.curve.delivery.model.AddVehicleRequest
import com.curve.delivery.model.LoginRequest
import com.curve.delivery.model.NewPasswordRequest
import com.curve.delivery.model.OTPVerifyRequest
import com.curve.delivery.model.RegisterRequest
import com.curve.delivery.model.ResetPassRequest
import com.curve.delivery.response.AddBankResp
import com.curve.delivery.response.AddVehicleResp
import com.curve.delivery.response.LoginResp
import com.curve.delivery.response.NewPasswordResp
import com.curve.delivery.response.OTPVerifyResp
import com.curve.delivery.response.RegisterResp
import com.curve.delivery.response.ResetPassResp
import com.curve.delivery.response.UploadImageResp
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @POST("delivery/auth/login")
    suspend fun hitLogin(@Body request: LoginRequest): LoginResp

    @POST("delivery/auth/login")
    suspend fun hitLogin1(@Body request: LoginRequest): Response<LoginResp>

    @POST("delivery/auth/register")
    suspend fun hitRegister1(@Body request: RegisterRequest): RegisterResp

    @POST("delivery/auth/register")
    suspend fun hitRegister(@Body request: RegisterRequest): Response<RegisterResp>

    @POST("delivery/auth/verifyOtp")
    suspend fun hitVerifyOtp1(
        @Header("accessToken") accessToken: String,
        @Body request: OTPVerifyRequest
    ): OTPVerifyResp

    @POST("delivery/auth/verifyOtp")
    suspend fun hitVerifyOtp(
        @Header("accessToken") accessToken: String,
        @Body request: OTPVerifyRequest
    ): Response<OTPVerifyResp>

    @POST("delivery/auth/addBank")
    suspend fun hitAddBank(
        @Header("accessToken") accessToken: String,
        @Body request: AddBankRequest): Response<AddBankResp>


    @POST("delivery/auth/addVechile")
    suspend fun hitAddVehicle(
        @Header("accessToken") accessToken: String,
        @Body request: AddVehicleRequest): Response<AddVehicleResp>


    @POST("delivery/auth/resetPassword")
    suspend fun hitResetPassword(
        @Header("accessToken") accessToken: String,
        @Body request: ResetPassRequest): Response<ResetPassResp>


    @POST("delivery/auth/newPassword")
    suspend fun hitNewPassword(
        @Header("accessToken") accessToken: String,
        @Body request: NewPasswordRequest): Response<NewPasswordResp>


    @Multipart
    @POST("delivery/auth/uploadFile")
    suspend fun uploadFile(
        @Part() upload_delivery_file: MultipartBody.Part? = null
    ): Response<UploadImageResp>


}