package com.curve.delivery.ui.otp
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeuisuite.ohteepee.OhTeePeeInput
import com.composeuisuite.ohteepee.configuration.OhTeePeeCellConfiguration
import com.composeuisuite.ohteepee.configuration.OhTeePeeConfigurations
import com.curve.delivery.R
import com.curve.delivery.databinding.LanguageSelectionDialogBinding
import com.curve.delivery.databinding.LayoutThankYouBinding
import com.curve.delivery.model.OTPVerifyRequest
import com.curve.delivery.model.ResetPassRequest
import com.curve.delivery.ui.createPassword.CreatePassScreen
import com.curve.delivery.ui.resetPassword.ResetScreen
import com.curve.delivery.ui.signup.SignupScreen
import com.curve.delivery.util.SharedPreference
import com.curve.delivery.util.moveActivity
import com.curve.delivery.util.showToast
import com.curve.delivery.viewModel.M1ViewModel
import com.mukeshsolanki.OTP_VIEW_TYPE_BORDER
import com.mukeshsolanki.OtpView
import kotlinx.coroutines.delay
import kotlin.math.log

class OTPScreen : ComponentActivity() {
    val viewModel: M1ViewModel by viewModels()

    companion object{
        var COUNTRY_CODE = ""
        var PHONE_NUMBER = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OTPScreenContent() }
        observer()
    }

    private fun observer() {
        viewModel.mOtpVerifyResp.observe(this) {
            Log.d("Response", it.toString())
            showToast(it.message)
            SharedPreference.get(this@OTPScreen).accessToken = it.data.accessToken
            if (intent.hasExtra("reset"))
            {
                moveActivity(CreatePassScreen())
            }

            if (intent.hasExtra("signup"))
            {
                startActivity(Intent(this@OTPScreen, SignupScreen::class.java).putExtra("vehicle", "vehicle"))

            }

        }
        viewModel.mError.observe(this) {
            Log.d("Error", it.toString())
            showToast(it.toString())
        }
    }

    @Composable
    fun OTPScreenContent() {
        var otpValue by remember { mutableStateOf("") }
        var timeLeft by remember { mutableStateOf(30) }
        var startTimer by remember { mutableStateOf(true) }
        var resend by remember { mutableStateOf("")}
        var color by remember { mutableStateOf( Color(0XFF848484))}
        var focous= LocalFocusManager.current


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 25.dp)
        )
        {
            Image(
                modifier = Modifier.clickable { onBackPressed() },
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "otp"
            )
            VerticalSpacer(5)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_login),
                    contentDescription = "otp"
                )
            }
            VerticalSpacer(36)
            Text(
                text = "OTP Verification", fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                fontSize = 18.sp,
                color = colorResource(id = R.color.black_333333)
            )
            VerticalSpacer(12)
            Text(
                text = "6 digit OTP has been sent to your registered Mobile Number.",
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                fontSize = 12.sp,
                color = colorResource(id = R.color.gray_9D9D9D)
            )
            VerticalSpacer(value = 20)
            var otpValue by remember { mutableStateOf("") }
            OtpView(
                otpText = otpValue,
                otpCount = 6,
                onOtpTextChange = {
                    otpValue = it
                    Log.d("Actual Value", otpValue)
                },
                charBackground = colorResource(id = R.color.white_F6F6F6),
                type = OTP_VIEW_TYPE_BORDER,
                password = true,
                containerSize = 50.dp,
                passwordChar = "*",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                charColor = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .focusRequester(FocusRequester()),
            )

            VerticalSpacer(value = 20)
            Row {


                ResendOtp() {
                    if (it)
                    {
                        otpValue= ""
                        focous.clearFocus(true)

                    }
                }


                
            }
            VerticalSpacer(value = 40)
            Button(
                onClick = {
                    val request = OTPVerifyRequest(otp = otpValue.toInt())
                    val token  = SharedPreference.get(this@OTPScreen).accessToken
                    viewModel.hitVerifyOtp(token,request)
                    Log.d("TAG", "LoginScreenContent: $token $request")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(id = R.color.green)
                )
            ) {
                Text(
                    text = "Submit",
                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                    fontSize = 14.sp
                )
            }
        }
    }

    @Composable
    fun ResendOtp(onResent:(Boolean)->Unit)
    {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth() ) {
            var timeLeft by remember { mutableStateOf(30) }
            var startTimer by remember { mutableStateOf(true) }
            var resend by remember { mutableStateOf("")}
            var color by remember { mutableStateOf( Color(0XFF848484))}
            Text(
                text = resend,
                fontSize = 12.sp,
                color = color,
                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                modifier = Modifier.clickable {
                    if (resend.toString().contains("Resend")) {
                        startTimer = true
                        onResent(true)
                        Log.d("TAG", "ResendOtp: $COUNTRY_CODE")
                        Log.d("TAG", "ResendOtp: $PHONE_NUMBER")
                        viewModel.hitResetPassword(
                            token = SharedPreference.get(this@OTPScreen).accessToken,
                            userRequest = ResetPassRequest(
                                countryCode = COUNTRY_CODE,
                                mobileNumber = PHONE_NUMBER
                            )
                        )

                    } else {
                        startTimer = false


                    } // Only clickable, no align needed here
                })
            val context = LocalContext.current
            if (startTimer)
            {
                LaunchedEffect(key1 = Unit) {
                    val countDownTimer = object : CountDownTimer(90000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            timeLeft = (millisUntilFinished / 1000).toInt()
                            color =Color(0XFF848484)
                            if (timeLeft.toString().length<2){
                                resend="00:0$timeLeft"
                            }
                            else {
                                resend = "00:"+timeLeft.toString()
                            }

                        }

                        override fun onFinish() {
                            timeLeft = 0
                            startTimer = false
                        }
                    }
                    countDownTimer.start()

                    // Delay for 1 second after countdown finishes to show 0
                    delay(1000)

                }
            }
            else
            {
                color= Color(0XFF33BD8C)
                resend = "Resend OTP"
            }

        }
    }





    @Composable
    fun VerticalSpacer(value: Int) {
        Spacer(modifier = Modifier.height(value.dp))
    }

    @Composable
    fun HorizontalSpacer(value: Int) {
        Spacer(modifier = Modifier.width(value.dp))
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewOTPScreenContent() {
        OTPScreenContent()
    }


}
