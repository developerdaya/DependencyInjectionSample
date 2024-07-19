package com.curve.delivery.ui.login

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.wear.compose.material.dialog.Dialog
import com.abi.simplecountrypicker.DialogCountryPicker
import com.curve.delivery.R
import com.curve.delivery.databinding.LanguageSelectionDialogBinding
import com.curve.delivery.model.LoginRequest
import com.curve.delivery.new_architecture.helper.CustomLoader
import com.curve.delivery.new_architecture.helper.EmpResource
import com.curve.delivery.new_architecture.helper.ErrorUtil
import com.curve.delivery.new_architecture.viewmodel.AuthViewModel
import com.curve.delivery.ui.home.HomeScreen
import com.curve.delivery.ui.otp.OTPScreen
import com.curve.delivery.ui.resetPassword.ResetScreen
import com.curve.delivery.ui.signup.SignupScreen
import com.curve.delivery.ui.underReview.UnderReview
import com.curve.delivery.util.Constant.showSnackBar
import com.curve.delivery.util.ErrorUtil.parseApiError
import com.curve.delivery.util.SharedPreference
import com.curve.delivery.util.moveActivity
import com.curve.delivery.util.moveActivityHaxExtra
import com.curve.delivery.util.showToast
import com.curve.delivery.util.showToastC
import com.curve.delivery.viewModel.M1ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen : ComponentActivity() {
    private val viewModel: M1ViewModel by viewModels()
    private val viewModel1: AuthViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            requestPermissions()
            if (!SharedPreference.get(this).isLanguageSelected)
            {
                showDialog(this@LoginScreen)
                SharedPreference.get(this).isLanguageSelected = true

            }
            LoginScreenContent(this)
        }
        observer1()
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 111
        )
    }

    fun showDialog(context: Context?) {
        val binding = LanguageSelectionDialogBinding.inflate(LayoutInflater.from(context))
        val mBuilder = AlertDialog.Builder(context).setView(binding.root).create()
        mBuilder.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        mBuilder.setCancelable(false)
        mBuilder.show()
        binding.mContinue.setOnClickListener {
            mBuilder.dismiss()
        }
        binding.mArabic.setOnClickListener {
            binding.mArabic.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.lng_arb_selected, 0, R.drawable.radio_button_active, 0
            )
            binding.mEnglish.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.lng_eng, 0, R.drawable.radio_button, 0
            )

        }
        binding.mEnglish.setOnClickListener {
            binding.mArabic.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.lng_arb, 0, R.drawable.radio_button, 0
            )
            binding.mEnglish.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.lng_eng_selected, 0, R.drawable.radio_button_active, 0
            )

        }


    }


    @Composable
    fun CustomDialog(
        title: String, message: String, onDismiss: () -> Unit, onConfirm: () -> Unit
    ) {
        Dialog(
            modifier = Modifier.background(Color.Transparent),
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnClickOutside = false, usePlatformDefaultWidth = false
            ),
            showDialog = true,
            scrollState = null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    ) {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message, fontSize = 16.sp, color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = { onDismiss() }) {
                                Text("Cancel")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(onClick = { onConfirm() }) {
                                Text("Confirm")
                            }
                        }
                    }
                }
            }
        }
    }

    fun observer1(){
        viewModel1.logonLiveData.observe(this) {
            when(it){
               is  EmpResource.Loading->{
                   CustomLoader.showLoader(this)
                }

                is EmpResource.Success->{
                    CustomLoader.hideLoader()
                    SharedPreference.get(this).accessToken =  it.value.data.accessToken
                    moveActivity(HomeScreen())
                    finishAffinity()
                    var isDocumentsUploaded = it.value.data.isDocumentsUploaded
                    var isOtpVerified = it.value.data.isOtpVerified
                    var isVechileDocUploaded = it.value.data.isVechileDocUploaded
                    var isBankDetailsUpdated = it.value.data.isBankDetailsUpdated
                    var isFullVerify = it.value.data.fullyVerify
                    var isBlocked = it.value.data.isBlocked
                    //fullyVerify : 0 // 0- pending 1- verified 2- fully reject 3- document reject

                    if(isBlocked)
                    {
                        showToast("You are Blocked by Admin")
                    }
                    else
                    {
                        if (isDocumentsUploaded && !isOtpVerified)
                        {
                            moveActivityHaxExtra("signup",OTPScreen())

                        }
                        else if (isDocumentsUploaded && isOtpVerified && !isVechileDocUploaded)
                        {
                            moveActivityHaxExtra("vehicle", SignupScreen())

                        } else if (isDocumentsUploaded && isOtpVerified && isVechileDocUploaded && !isBankDetailsUpdated)
                        {
                            moveActivityHaxExtra("bank", SignupScreen())
                        }

                        else if (isFullVerify == 0)
                        {
                            moveActivity(UnderReview())

                        } else if ( isFullVerify == 1) {
                            moveActivity(HomeScreen())
                        }
                        else if ( isFullVerify == 2) {
                            showToast("Your Account Has been Rejected by Admin, Please Contact Admin")

                        }

                        else if ( isFullVerify == 3)
                        {
                            showToast("Documented Rejected, Please Upload Document Again.")
                            moveActivityHaxExtra("document", SignupScreen())

                        }
                    }
                }

                is EmpResource.Failure->{
                    CustomLoader.hideLoader()
                    ErrorUtil.handlerGeneralError(this, it.throwable!!)
                }
            }
        }
    }

    fun observer() {
        viewModel.mLoginResp.observe(this)
        {
            SharedPreference.get(this).accessToken = it.data.accessToken
            moveActivity(HomeScreen())
            finishAffinity()
            var isDocumentsUploaded = it.data.isDocumentsUploaded
            var isOtpVerified = it.data.isOtpVerified
            var isVechileDocUploaded = it.data.isVechileDocUploaded
            var isBankDetailsUpdated = it.data.isBankDetailsUpdated
            var isFullVerify = it.data.fullyVerify
            var isBlocked = it.data.isBlocked
            //fullyVerify : 0 // 0- pending 1- verified 2- fully reject 3- document reject

            if(isBlocked)
            {
                showToast("You are Blocked by Admin")
            }
            else
            {
                if (isDocumentsUploaded && !isOtpVerified)
                {
                    moveActivityHaxExtra("signup",OTPScreen())

                }
                else if (isDocumentsUploaded && isOtpVerified && !isVechileDocUploaded)
                {
                    moveActivityHaxExtra("vehicle", SignupScreen())

                } else if (isDocumentsUploaded && isOtpVerified && isVechileDocUploaded && !isBankDetailsUpdated)
                {
                    moveActivityHaxExtra("bank", SignupScreen())
                }

                else if (isFullVerify == 0)
                {
                    moveActivity(UnderReview())

                } else if ( isFullVerify == 1) {
                    moveActivity(HomeScreen())
                }
                 else if ( isFullVerify == 2) {
                    showToast("Your Account Has been Rejected by Admin, Please Contact Admin")

                }

                 else if ( isFullVerify == 3)
                 {
                    showToast("Documented Rejected, Please Upload Document Again.")
                    moveActivityHaxExtra("document", SignupScreen())

                }


            }



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
            painter = painterResource(id = image), contentDescription = null, modifier = modifier1
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    fun LoginScreenContent(myContext: Context? = null) {
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
                    text = "Login",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.black_333333)
                )

                VerticalSpacer(value = 12)

                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing.",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                    color = colorResource(id = R.color.gray_9D9D9D).copy(alpha =0.5f)
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
                                Color.White, shape = RoundedCornerShape(10.dp)
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TextField(value = mobileNumber,
                                onValueChange = {
                                    if (it.length < 15) {
                                        mobileNumber = it
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                placeholder = {
                                    Text(
                                        "Enter Mobile Number",
                                        color = colorResource(id = R.color.gray_9D9D9D).copy(alpha = 0.5f),
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(start = 8.dp),

                                    )

                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    disabledContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                                leadingIcon = {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            DialogCountryPicker(
                                                defaultCountryIdentifier = "kw",
                                                pickedCountry = { countryCode = it.countryCode },
                                                countryCodeTextColorAndIconColor = colorResource(id = R.color.black_161616),
                                                trailingIconComposable = {},
                                                isCircleShapeFlag = false,
                                                isCountryFlagVisible = false,
                                            )
                                            VerticalDivider(
                                                modifier = Modifier
                                                    .width(1.dp)
                                                    .padding(vertical = 8.dp),
                                                color = colorResource(id = R.color.line_color)
                                            )
                                        }
                                    }
                                })
                        }

                        VerticalSpacer(value = 12)

                        TextField(value = password,
                            onValueChange = {
                                if (it.length <= 20) {
                                    password = it
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            placeholder = {
                                Text("Please Enter Password", color = colorResource(id = R.color.gray_9D9D9D).copy(alpha = 0.5f), fontSize = 12.sp)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 14.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(modifier = Modifier.clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                myContext?.let {
                                    moveActivity(ResetScreen())
                                }
                            },
                                text = "Reset Password",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = colorResource(id = R.color.green)
                            )
                        }

                        VerticalSpacer(18)

                        Button(
                            onClick = {
                                if (validation(mobileNumber, password)) {
                                    myContext?.let { context ->
                                        val request = LoginRequest(
                                            countryCode = countryCode,
                                            mobileNumber = mobileNumber,
                                            lat = 0.0,
                                            long = 0.0,
                                            password = password
                                        )
                                        Log.d("daya", "LoginScreenContent: $request")
                                        viewModel1.hitLogin(request)
                                    }
                                }

                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.green)
                            ),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            Text(
                                text = "Login",
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Don't have an account?",
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        color = colorResource(id = R.color.black_05)
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                moveActivityHaxExtra("basic", SignupScreen())

                            },
                        text = "Signup",
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                        color = colorResource(id = R.color.green)
                    )
                }
            }
        }
    }

    fun validation(mobile: String, password: String): Boolean {
        if (mobile.isEmpty()) {
            showToast("Enter Mobile Number")
            return false
        } else if (mobile.length < 7) {
            showToast("Mobile Number should be of minimum 7 digits long")
            return false
        } else if (password.isEmpty()) {
            showToast("Please enter password Number")
            return false
        }else if(mobile.length !=10 && mobile.length<=15)
            showToast("Please enter valid mobile number")
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

