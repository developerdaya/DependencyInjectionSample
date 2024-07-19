package com.curve.delivery.new_architecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curve.delivery.R
import com.curve.delivery.model.LoginRequest
import com.curve.delivery.MyApplication
import com.curve.delivery.model.OTPVerifyRequest
import com.curve.delivery.model.RegisterRequest
import com.curve.delivery.new_architecture.helper.EmpResource
import com.curve.delivery.new_architecture.helper.InternetConnection
import com.curve.delivery.new_architecture.repository.AuthRepository
import com.curve.delivery.response.LoginResp
import com.curve.delivery.response.OTPVerifyResp
import com.curve.delivery.response.RegisterResp
import com.curve.delivery.util.showToastC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(val authRepository: AuthRepository):ViewModel() {
    var isNetworkAvailable = MutableLiveData<Boolean>(true)

    //this method is to check the internet connection
    private fun checkInternetConnection(): Boolean {
        return if (InternetConnection.checkConnection(MyApplication.appContext)) {
            true
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                isNetworkAvailable.value = false
            }
            false
        }
    }


    private val _logonLiveData = MutableLiveData<EmpResource<LoginResp>>()
    val logonLiveData: LiveData<EmpResource<LoginResp>>
        get() = _logonLiveData

    fun hitLogin(model: LoginRequest) {
        if (checkInternetConnection())
            viewModelScope.launch {
                _logonLiveData.value = EmpResource.Loading
                _logonLiveData.value = authRepository.hitLogin(model)
            }
        else {
            showToastC(MyApplication.appContext, MyApplication.appContext.getString(R.string.no_network_found))
        }
    }

    private val _signupLiveData = MutableLiveData<EmpResource<RegisterResp>>()
    val signupLiveData: LiveData<EmpResource<RegisterResp>>
        get() = _signupLiveData

    fun hitRegister(model: RegisterRequest) {
        if (checkInternetConnection())
            viewModelScope.launch {
                _signupLiveData.value = EmpResource.Loading
                _signupLiveData.value = authRepository.hitRegister(model)
            }
        else {
            showToastC(MyApplication.appContext, MyApplication.appContext.getString(R.string.no_network_found))
        }
    }

    private val _otpverifyLiveData = MutableLiveData<EmpResource<OTPVerifyResp>>()
    val otpverifyLiveData: LiveData<EmpResource<OTPVerifyResp>>
        get() = _otpverifyLiveData

    fun hitOTPVerifyRequest(accessToken:String,model: OTPVerifyRequest) {
        if (checkInternetConnection())
            viewModelScope.launch {
                _otpverifyLiveData.value = EmpResource.Loading
                _otpverifyLiveData.value = authRepository.hitVerifyOtp(accessToken,model)
            }
        else {
            showToastC(MyApplication.appContext, MyApplication.appContext.getString(R.string.no_network_found))
        }
    }
}