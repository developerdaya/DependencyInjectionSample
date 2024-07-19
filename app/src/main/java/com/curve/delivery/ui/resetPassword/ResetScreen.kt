package com.curve.delivery.ui.resetPassword

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abi.simplecountrypicker.DialogCountryPicker
import com.curve.delivery.R
import com.curve.delivery.model.LoginRequest
import com.curve.delivery.model.ResetPassRequest
import com.curve.delivery.ui.home.HomeScreen
import com.curve.delivery.ui.otp.OTPScreen
import com.curve.delivery.ui.signup.SignupScreen
import com.curve.delivery.util.SharedPreference
import com.curve.delivery.util.moveActivity
import com.curve.delivery.util.moveActivityHaxExtra
import com.curve.delivery.util.showToast
import com.curve.delivery.viewModel.M1ViewModel

class ResetScreen : ComponentActivity() {
    private val viewModel: M1ViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ResetScreenContent(this) }
        observer()
    }
     fun observer() {
        viewModel.mRestPasswordResp.observe(this)
        {
            SharedPreference.get(this).accessToken = it.data.accessToken
            moveActivityHaxExtra("reset",OTPScreen())
            finishAffinity()

        }
        viewModel.mError.observe(this) {
            showToast(it)
        }



    }

    @Composable
    fun textViewBold(
        text: String,
        fontSize: Int,
        color: Int,
        textAlign1: TextAlign?,
        modifier1: Modifier = Modifier
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
            color = colorResource(id = color),
            textAlign = textAlign1,
            modifier = modifier1
        )
    }

    @Composable
    fun textViewSemiBold(
        text: String,
        fontSize: Int,
        color: Int,
        modifier1: Modifier = Modifier,
        textAlign1: TextAlign? = null
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
            color = colorResource(color),
            modifier = modifier1,
            textAlign = textAlign1
        )
    }

    @Composable
    fun textViewMedium(
        text: String,
        fontSize: Int,
        color: Int,
        modifier1: Modifier = Modifier,
        textAlign1: TextAlign? = null
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
            color = colorResource(color),
            modifier = modifier1,
            textAlign = textAlign1
        )
    }

    @Composable
    fun textViewRegular(text: String, fontSize: Int, color: Int) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
            color = colorResource(color)
        )
    }

    @Composable
    fun imageView(image: Int, modifier1: Modifier = Modifier) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = modifier1
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    fun  ResetScreenContent(myContext: Context? = null) {
        var mobileNumber by remember { mutableStateOf(value = "") }
        var countryCode by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            var mobileNumber by remember { mutableStateOf(value = "") }
            var password by remember { mutableStateOf(value = "") }
            var countryCode by remember { mutableStateOf("") }

            Image(painter = painterResource(id = R.drawable.logo_login), contentDescription = "")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                VerticalSpacer(value = 36)

                Text(
                    text = "Reset Password",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.black_333333)
                )

                VerticalSpacer(value = 12)

                Text(
                    text  = "Lorem ipsum dolor sit amet, consectetur adipiscing.",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                    color = colorResource(id = R.color.gray_9D9D9D)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .background(
                            color = colorResource(id = R.color.white_F6F6F6),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.background(
                                Color.White,
                                shape = RoundedCornerShape(10.dp)
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TextField(
                                value = mobileNumber,
                                onValueChange = {
                                    if (it.length < 11)
                                    {
                                        mobileNumber = it
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                ,
                                placeholder = {
                                    Text(
                                        "Enter Mobile Number",
                                        color = colorResource(id = R.color.gray_9D9D9D)
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                leadingIcon = {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            DialogCountryPicker(
                                                defaultCountryIdentifier = "kw",
                                                pickedCountry = { countryCode = it.countryCode },
                                                countryCodeTextColorAndIconColor = colorResource(id = R.color.black_161616),
                                                trailingIconComposable = {},
                                                isCircleShapeFlag = false,
                                                isCountryFlagVisible = false
                                            )
                                            VerticalDivider(
                                                modifier = Modifier
                                                    .width(1.dp)
                                                    .padding(vertical = 8.dp),
                                                color = colorResource(id = R.color.line_color)
                                            )
                                        }
                                    }
                                }
                            )
                        }

                        VerticalSpacer(value = 12)
                        VerticalSpacer(18)
                        Button(
                            onClick = {
                                if(validation(mobileNumber))
                                {
                                    myContext?.let { context ->
                                        val request = ResetPassRequest(
                                            countryCode = countryCode,
                                            mobileNumber = mobileNumber)

                                        Log.d("TAG", "LoginScreenContent: $request")
                                        OTPScreen.COUNTRY_CODE = countryCode
                                        OTPScreen.PHONE_NUMBER = mobileNumber

                                        val token  = SharedPreference.get(this@ResetScreen).accessToken
                                        viewModel.hitResetPassword(token,request)


                                    }
                                }

                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.green)
                            ),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            Text(text = "Continue", color = Color.White, modifier = Modifier.padding(vertical = 5.dp))
                        }
                    }
                }

            }
        }
    }

    fun validation(mobile:String): Boolean {
        if (mobile.isEmpty())
        {
            showToast("Enter Mobile Number")
            return false
        } else if (mobile.length < 10)
        {
            showToast("Enter Valid Mobile Number")
            return false
        }
        return true

    }

    @Composable
    fun VerticalSpacer(value: Int) {
        Spacer(modifier = Modifier.height(value.dp))
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Preview(showBackground = true)
    @Composable
    fun ResetScreenPreview() {
        ResetScreenContent()
    }
}
