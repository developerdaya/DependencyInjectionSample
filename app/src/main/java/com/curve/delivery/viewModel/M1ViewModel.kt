package com.curve.delivery.viewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.curve.delivery.response.ResponseSignup
import com.curve.delivery.response.UData
import com.curve.delivery.response.UploadImageResp
import com.curve.delivery.util.ErrorUtil
import com.curve.delivery.util.RetrofitClient
import com.curve.delivery.util.ShowError
import com.curve.delivery.util.handleApiResponse
import com.curve.delivery.util.showErrorMsg
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class M1ViewModel : ViewModel() {
    val mLoginResp = MutableLiveData<LoginResp>()
    val mSignupResp = MutableLiveData<RegisterResp>()
    val mOtpVerifyResp = MutableLiveData<OTPVerifyResp>()
    val mAddBankResp = MutableLiveData<AddBankResp>()
    val mAddVehicleResp = MutableLiveData<AddVehicleResp>()
    val mRestPasswordResp = MutableLiveData<ResetPassResp>()
    val mNewPasswordResp = MutableLiveData<NewPasswordResp>()
    val mUploadImageResp = MutableLiveData<UploadImageResp>()
    var mError = MutableLiveData<String>()

    val signupType = MutableLiveData<Int>()

    var mProfileImgUrl = MutableLiveData<String>()
    var mBasicFrontUrl = MutableLiveData<String>()
    var mBasicBackUrl = MutableLiveData<String>()
    var mVehicleUrl1 = MutableLiveData<String>()
    var mVehicleUrl2 = MutableLiveData<String>()
    var mVehicleUrl3 = MutableLiveData<String>()




    fun updateSignupType(newType: Int) {
        signupType.value = newType
    }



    fun uploadFile(userUpload:MultipartBody.Part?)
    {
        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.uploadFile(userUpload) },
                onSuccess = { mUploadImageResp.value = it },
                onError = { mError.value = it })
        }
    }







    fun hitLogin(userRequest: LoginRequest) {
        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.hitLogin1(userRequest) },
                onSuccess = { mLoginResp.value = it },
                onError = { mError.value = it })
        }
    }







    fun hitRegister(userRequest: RegisterRequest)
    {
        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.hitRegister(userRequest) },
                onSuccess = { mSignupResp.value = it },
                onError = { mError.value = it })
        }
    }



 fun hitVerifyOtp(token:String,userRequest: OTPVerifyRequest)
    {
        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.hitVerifyOtp(token,userRequest) },
                onSuccess = { mOtpVerifyResp.value = it },
                onError = { mError.value = it })
        }
    }



     fun hitAddBank(token: String,userRequest: AddBankRequest)
    {
        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.hitAddBank(token,userRequest) },
                onSuccess = { mAddBankResp.value = it },
                onError = { mError.value = it })
        }
    }



        fun hitAddVehicle(token:String,userRequest: AddVehicleRequest)
    {
        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.hitAddVehicle(token,userRequest) },
                onSuccess = { mAddVehicleResp.value = it },
                onError = { mError.value = it })
        }
    }




            fun hitResetPassword(token: String,userRequest: ResetPassRequest)
    {

        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.hitResetPassword(token,userRequest) },
                onSuccess = { mRestPasswordResp.value = it },
                onError = { mError.value = it })
        }
    }





            fun hitNewPassword(token:String,userRequest: NewPasswordRequest)
    {

        viewModelScope.launch {
            handleApiResponse(
                call = { RetrofitClient.apiService.hitNewPassword(token,userRequest) },
                onSuccess = { mNewPasswordResp.value = it },
                onError = { mError.value = it })
        }
    }










}