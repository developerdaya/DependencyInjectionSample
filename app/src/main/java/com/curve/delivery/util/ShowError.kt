package com.curve.delivery.util
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
object ShowError {
    fun apiError(errorBody: ResponseBody?): String {
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
}
