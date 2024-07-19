package com.curve.delivery.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            // Log and handle network errors
            println("Network error: ${e.message}")
            e.printStackTrace()
            throw e
        }

        // Log the response
        val responseBody = response.body
        val responseBodyString = responseBody?.string() ?: "No Response Body"
        val logMessage = """
            URL: ${response.request.url}
            Method: ${response.request.method}
            Headers: ${response.headers}
            Status Code: ${response.code}
            Response Body: $responseBodyString
        """.trimIndent()
        println(logMessage)

        // Re-create the response before returning it because the response body can only be consumed once
        return response.newBuilder()
            .body(responseBodyString.toResponseBody(responseBody?.contentType()))
            .build()
    }
}

fun String.toResponseBody(contentType: okhttp3.MediaType?): okhttp3.ResponseBody {
    return okhttp3.ResponseBody.create(contentType, this)
}
