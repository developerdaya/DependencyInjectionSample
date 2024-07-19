package com.curve.delivery.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.curve.delivery.R
import com.curve.delivery.util.Constant.PASSWORD_PATTERN
import com.google.gson.Gson
import com.lanistafottball.base.ErrorModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern


suspend fun <T> handleApiResponse(
    call: suspend () -> Response<T>,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            val errorMessage = ErrorUtil.parseApiError(response.errorBody())
            onError(errorMessage)
        }
    } catch (e: HttpException) {
        val errorMessage = ErrorUtil.parseApiError(e.response()?.errorBody())
        onError(errorMessage)
    } catch (e: Exception) {
        onError(e.message ?: "Somthing Went Wrong")
    }
}


fun ComponentActivity.moveActivity(activity: ComponentActivity)
{
   startActivity(Intent(this, activity::class.java))
}


fun ComponentActivity.moveActivityHaxExtra(msg: String,activity: ComponentActivity)
{
   startActivity(Intent(this, activity::class.java)
       .putExtra(msg,msg))
}




fun ComponentActivity.showToast(msg:String)
{
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun showToastC(context: Context,message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}




fun ComponentActivity.checkPermissions(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED)
    } else {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }
}

fun ComponentActivity.getPath(uri: Uri?): String? {
    val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
    val cursor = contentResolver.query(uri!!, projection, null, null, null) ?: return null
    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    val s = cursor.getString(column_index)
    cursor.close()
    return s
}

fun convertFormFileToMultipartBody(key: String, file: File?): MultipartBody.Part? = file?.let {
    MultipartBody.Part.createFormData(
        key, it.name,
        it.asRequestBody("image/*".toMediaTypeOrNull())
    )

}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(inputDate: String): String {
    // Parse the input date string
    val formatterInput = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date = LocalDate.parse(inputDate, formatterInput)

    // Format the date to "dd MMM yyyy" (e.g., "01 Apr 2024")
    val formatterOutput = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val formattedDate = date.format(formatterOutput)

    return formattedDate
}

/*.....................fun getImage Uri........................*/
fun ComponentActivity.getImageUri(inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        this.contentResolver, inImage, "IMG_${System.currentTimeMillis()}", null
    )
    return Uri.parse(path)
}

fun displayErrorMessage(context: Context, exception: HttpException) {
    try {
        val errorBody = Gson().fromJson(
            exception.response()!!.errorBody()!!.charStream(),
            ErrorModel::class.java
        )

        showToastC(context, errorBody.message)


    } catch (e: Exception) {
        showToastC(context, context.getString(R.string.something_went_wrong))
    }
}



fun transparentStatusBar(activity: ComponentActivity) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.window.setDecorFitsSystemWindows(false)
        activity.window.statusBarColor = Color.Transparent.toArgb()
        activity.window.navigationBarColor = Color.Transparent.toArgb()
    } else {
        @Suppress("DEPRECATION")
        activity.window.decorView.systemUiVisibility =
            activity.window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        @Suppress("DEPRECATION")
        activity.window.statusBarColor = Color.Transparent.toArgb()
    }
}

fun String.isValidPassword(): Boolean {
    return if (this.length < 8) {
        false
    } else {
        val matcher: Matcher
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(this)
        matcher.matches()
    }
}

fun getddDate(times: Long): String {
    val timestamp = times // Replace this with your timestamp

    val date = Date(timestamp)
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = sdf.format(date)

    return formattedDate
}

fun isGPSEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun getAddress(latitude: Double, longitude: Double, context: Activity): String {
    if (latitude != 0.0) {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addresses: MutableList<android.location.Address>? = null
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1)
        } catch (e: IOException) {
            Log.e("AddressHelper", "Error getting address from location: $e")
        }

        return addresses?.get(0)?.getAddressLine(0).toString()
    } else {
        return ""
    }
}

fun getDateYearDate(times: Long): String {
    val date = Date(times)
    val sdf = SimpleDateFormat("dd MMM yy", Locale.getDefault())
    val formattedDate = sdf.format(date)

    return formattedDate
}

fun ComponentActivity.setWHiteStatusBar() {
    window.statusBarColor = Color.White.toArgb()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun showErrorMsg(error: String): String {
    val errorBody = error
    val errorMessage = if (!errorBody.isNullOrEmpty()) {
        try {
            val jsonObj = JSONObject(errorBody)
            jsonObj.getString("message")

        } catch (e: Exception) {
            "Unknown error"
        }
    } else {
        "Unknown error"
    }
    return errorMessage
}

fun ComponentActivity.transparentStatusBar(activity: ComponentActivity) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.window.setDecorFitsSystemWindows(false)
        activity.window.statusBarColor = Color.Transparent.toArgb()
        activity.window.navigationBarColor = Color.Transparent.toArgb()
    } else {
        @Suppress("DEPRECATION")
        activity.window.decorView.systemUiVisibility =
            activity.window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        @Suppress("DEPRECATION")
        activity.window.statusBarColor = Color.Transparent.toArgb()
    }
}
