package com.curve.delivery.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.curve.delivery.R


//--------------------------------Define Preference:: DeveloperDaya--------------------------------//

class SharedPreference(context: Context) {
    val preference: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preference.edit()

    companion object {
        var instance: SharedPreference? = null
        fun get(ctx: Context): SharedPreference {
            if (instance == null) {
                instance = SharedPreference(ctx)
            }
            return instance!!
        }
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is Int -> edit { it.putInt(key, value) }
            is String? -> edit { it.putString(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> Log.e("TAG", "Setting shared pref failed for key: $key and value: $value ")
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
    //--------------------------------Define Your Global Variable Here:: DeveloperDaya--------------------------------//

    var accessToken: String
        get() = preference["accessToken", ""]?:""
        set(value) = preference.set("accessToken", value)
    var isLogin: Boolean
        get() = preference["isLogin", false]?:false
        set(value) = preference.set("isLogin", value)

     var isLanguageSelected: Boolean
        get() = preference["isLanguageSelected", false]?:false
        set(value) = preference.set("isLanguageSelected", value)


    var name:String
        get() = preference["name",""]?:""
        set(value) = preference.set("name",value)

    fun removePreferences() {
        editor.clear().apply()
    }

}