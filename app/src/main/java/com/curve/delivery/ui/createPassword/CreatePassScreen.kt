package com.curve.delivery.ui.createPassword


import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abi.simplecountrypicker.DialogCountryPicker
import com.curve.delivery.R
import com.curve.delivery.model.NewPasswordRequest
import com.curve.delivery.model.ResetPassRequest
import com.curve.delivery.ui.login.LoginScreen
import com.curve.delivery.ui.otp.OTPScreen
import com.curve.delivery.ui.signup.SignupScreen
import com.curve.delivery.util.SharedPreference
import com.curve.delivery.util.moveActivity
import com.curve.delivery.util.showToast
import com.curve.delivery.util.showToastC
import com.curve.delivery.viewModel.M1ViewModel

class CreatePassScreen : ComponentActivity() {
    private val viewModel: M1ViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LoginScreenContent(this) }
        observer()
    }
    fun observer() {
        viewModel.mNewPasswordResp.observe(this) {
            showToast(it.message)
            moveActivity(LoginScreen())
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
        modifier1: Modifier = Modifier)
    {
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
        textAlign1: TextAlign? = null)
    {
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
        textAlign1: TextAlign? = null)
    {
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
    fun textViewRegular(text: String, fontSize: Int, color: Int)
    {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
            color = colorResource(color)
        )
    }

    @Composable
    fun imageView(image: Int, modifier1: Modifier = Modifier)
    {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = modifier1
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    fun LoginScreenContent(myContext: Context? = null) {
        var mobileNumber by remember { mutableStateOf(value = "") }
        var countryCode by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top)
        {
            var mobileNumber by remember { mutableStateOf(value = "") }
            var passwordCurrent by remember { mutableStateOf(value = "") }
            var passwordNew by remember { mutableStateOf("") }

            Image(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable {
                        onBackPressed()
                    },
                painter = painterResource(id = R.drawable.back_button), contentDescription = "")
            Image(modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.logo_login), contentDescription = "")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                VerticalSpacer(value = 36)

                Text(
                    text = "Create Password",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.black_333333)
                )

                VerticalSpacer(value = 12)

                Text(
                    text    = "Please create new password",
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
                        TextField(
                            value = passwordCurrent,
                            onValueChange = { if (it.length <= 20) { passwordCurrent = it } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            visualTransformation = PasswordVisualTransformation(),

                            placeholder = {
                                Text("Current Password",

                                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                    color = colorResource(id = R.color.gray_9D9D9D))
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )

                        VerticalSpacer(value = 12)
                        TextField(
                            value = passwordNew,
                            onValueChange = { if (it.length <= 20) { passwordNew = it } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            visualTransformation = PasswordVisualTransformation(),

                            placeholder = {
                                Text("New Password",
                                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                    color = colorResource(id = R.color.gray_9D9D9D))
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        VerticalSpacer(18)
                        Button(
                            onClick = {
                                if(validation(passwordCurrent,passwordNew))
                                {
                                    myContext?.let { context ->
                                        val request = NewPasswordRequest(currentPassword = passwordCurrent, password = passwordNew)
                                        Log.d("daya", "LoginScreenContent: $request")
                                        viewModel.hitNewPassword(SharedPreference.get(this@CreatePassScreen).accessToken,request)
                                    }
                                }

                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.green)),
                            contentPadding = PaddingValues(10.dp),
                            shape =RoundedCornerShape(25.dp),
                            elevation = null,
                            border = null
                        ) {
                            Text(text = "Save", color = Color.White, modifier = Modifier.padding(vertical = 5.dp))
                        }
                    }
                }

               /* Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                )
                {
                    Text(
                        text = "Don't have an account?",
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        color = colorResource(id = R.color.black_05)
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                myContext?.let { context ->
                               moveActivity(SignupScreen())
                                }
                            },
                        text = "Signup",
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                        color = colorResource(id = R.color.green)
                    )
                }*/
            }
        }
    }

    fun validation(password: String,confirmPassword: String): Boolean
    {
        if (password.isEmpty()) {
            showToast("Please enter Current Password")
            return false
        }

        if (confirmPassword.isEmpty())
        {
           showToast("Please enter New Password")
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
    fun LoginScreenPreview() {
        LoginScreenContent()
    }
}
