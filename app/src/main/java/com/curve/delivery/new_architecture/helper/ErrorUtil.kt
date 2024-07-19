package com.curve.delivery.new_architecture.helper

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.curve.delivery.util.Constant.showSnackBar
import com.curve.delivery.util.ErrorUtil.parseApiError
import com.curve.delivery.util.ErrorUtil.parseApiError1
import com.curve.delivery.util.showToastC
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object ErrorUtil {
    fun handlerGeneralError(context: Context, throwable: Throwable) {
        throwable.printStackTrace()
        when (throwable) {
            is ConnectException -> showToastC(context, "Please turn on Internet")
            is SocketTimeoutException -> showToastC(context, "Socket Time Out Exception")
            is UnknownHostException -> showToastC(context, "No Internet Connection")
            is InternalError -> showToastC(context, "Internal Server Error")
            is HttpException -> {
                val errorMessage = parseApiError1(throwable.response()?.errorBody())
                showToastC(context, errorMessage)
            }
            else -> {
                showToastC(context, "Something went wrong")
            }
        }
    }

}


