package com.curve.delivery.util

import com.google.gson.Gson


object GsonUtil {

    private var gson: Gson? = null
    fun getGsonInstance(): Gson
    {
        if (gson == null)
            gson = Gson()
        return gson!!
    }
}