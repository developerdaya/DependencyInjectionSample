package com.curve.delivery.base

import androidx.lifecycle.ViewModel
import com.curve.delivery.util.ApiInterface
import com.curve.delivery.util.RetrofitUtil

abstract class BaseViewModel : ViewModel() {
    val api: ApiInterface by lazy { RetrofitUtil.createBaseApiService() }
}