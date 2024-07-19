package com.curve.delivery.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.curve.delivery.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Constant {
    const  val baseUrl="https://www.curveapp.co/api/v1/"
    const val EMAIL_PATTERN = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    const val PASSWORD_PATTERN ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&#])[A-Za-z\\d$@$!%*?&#]{7,15}"

    fun showSnackBar(context: Context, msg: String?) {
        val snackbar = Snackbar.make(
            (context as Activity).findViewById(android.R.id.content),
            msg!!, Snackbar.LENGTH_LONG
        )

        val snackBarView = snackbar.view
        snackBarView.minimumHeight = 10
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_003))
        val tv =
            snackBarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        tv.textSize = 13f
        tv.setTextColor(ContextCompat.getColor(context, R.color.white))
        snackbar.show()
    }
}