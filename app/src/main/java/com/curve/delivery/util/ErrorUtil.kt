package com.curve.delivery.util


import android.content.Context
import android.widget.Toast
import com.curve.delivery.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException


object ErrorUtil {
    fun parseApiError(errorBody: ResponseBody?): String {
        return if (errorBody != null) {
            try {
                val errorBodyString = errorBody.string()
                val jsonObject = Gson().fromJson(errorBodyString, JsonObject::class.java)
                jsonObject["message"]?.asString ?: "Something went wrong"
            } catch (e: Exception) {
                "Something went wrong"
            }
        } else {
            "Something went wrong"
        }
    }

    fun parseApiError1(errorBody: ResponseBody?): String {
        return if (errorBody != null) {
            try {
                val errorBodyString = errorBody.string()
                val jsonObject = JSONObject(errorBodyString)
                jsonObject.optString("message")?:"Something went wrong"
            } catch (e: Exception) {
                "Something went wrong"
            }
        } else {
            "Something went wrong"
        }
    }

    // Show error message based on throwable
    fun showError(context: Context?, throwable: Throwable) {
        throwable.printStackTrace()
        if (context == null) return
        when (throwable) {
            is ConnectException -> showToast(
                context, context.getString(R.string.network_error_please_try_later)
            )

            is SocketTimeoutException -> showToast(
                context, context.getString(R.string.connection_lost_please_try_later)
            )

            is UnknownHostException -> showToast(
                context, context.getString(R.string.server_error_please_try_later)
            )

            is HttpException -> {
                try {
                    when (throwable.code()) {
                        in 400..499 -> {
                            displayErrorMessage(context, throwable)
                        }
                        in 500..599 -> {
                            displayErrorMessage(context, throwable)
                        }
                        else -> {
                            displayErrorMessage(context, throwable)
                        }
                    }
                } catch (e: Exception) {
                    showToast(context, context.getString(R.string.something_went_wrong))
                }
            }
            else -> showToast(context, context.getString(R.string.something_went_wrong))
        }
    }

    private fun displayErrorMessage(context: Context, throwable: HttpException) {
        val errorMessage = parseApiError(throwable.response()?.errorBody())
        showToast(context, errorMessage)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
