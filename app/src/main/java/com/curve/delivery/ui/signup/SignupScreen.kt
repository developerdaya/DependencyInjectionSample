package com.curve.delivery.ui.signup

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import coil.compose.rememberImagePainter
import com.abi.simplecountrypicker.DialogCountryPicker
import com.curve.delivery.R
import com.curve.delivery.R.color
import com.curve.delivery.R.drawable
import com.curve.delivery.R.font
import com.curve.delivery.databinding.LayoutCameraGalleryBinding
import com.curve.delivery.databinding.LayoutSelectGenderBinding
import com.curve.delivery.databinding.LayoutSelectLanguageBinding
import com.curve.delivery.databinding.LayoutThankYouBinding
import com.curve.delivery.model.AddBankRequest
import com.curve.delivery.model.AddVehicleRequest
import com.curve.delivery.model.RegisterRequest
import com.curve.delivery.response.LoginResp
import com.curve.delivery.ui.home.HomeScreen
import com.curve.delivery.ui.otp.OTPScreen
import com.curve.delivery.ui.underReview.UnderReview
import com.curve.delivery.util.SharedPreference
import com.curve.delivery.util.checkPermissions
import com.curve.delivery.util.convertFormFileToMultipartBody
import com.curve.delivery.util.moveActivity
import com.curve.delivery.util.moveActivityHaxExtra
import com.curve.delivery.util.showToast
import com.curve.delivery.util.showToastC
import com.curve.delivery.viewModel.M1ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar
import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.Bitmap
import androidx.compose.ui.text.input.ImeAction
import coil.request.ImageRequest
import com.curve.delivery.new_architecture.viewmodel.AuthViewModel
import com.curve.delivery.util.Constant
import java.text.SimpleDateFormat
import java.util.Date


class SignupScreen : ComponentActivity() {
    private val viewModel: M1ViewModel by viewModels()
    private val viewModel1:AuthViewModel by viewModels()

    var mImageType = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(color.white)
        getIntentDat()
        setContent { SignupScreenContent(viewModel) }
        observer()
    }
    fun observer() {
        viewModel.mUploadImageResp.observe(this)
        {
           when(mImageType)
           {
               1 -> viewModel.mProfileImgUrl.value = it.data
               2 -> viewModel.mBasicFrontUrl.value = it.data
               3 -> viewModel.mBasicBackUrl.value = it.data
               4 -> viewModel.mVehicleUrl1.value = it.data
               5 -> viewModel.mVehicleUrl2.value = it.data
               6 -> viewModel.mVehicleUrl3.value = it.data
           }
        }
        viewModel.mError.observe(this) {
            showToast("$it")


            Log.d("daya", "observerAAA:$it ::::::::::")
        }
        viewModel.mAddVehicleResp.observe(this)
        {
            showToast(it.message.toString())
            viewModel.updateSignupType(3)
        }
        viewModel.mSignupResp.observe(this)
        {
            showToast(it.message.toString())
            OTPScreen.COUNTRY_CODE = it.data.countryCode
            OTPScreen.PHONE_NUMBER = it.data.mobileNumber
            SharedPreference.get(this@SignupScreen).accessToken = it.data.accessToken
            moveActivityHaxExtra("signup",OTPScreen())
        }

        viewModel.mAddBankResp.observe(this)
        {
            showToast(it.message.toString())

        }


    }

    @Composable
    fun UploadFrontBackDocument(
        mBasicFrontUrl: String,
        mBasicBackUrl: String)
    {
        val context = LocalContext.current
        val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->

                uri?.let {
                    val imageFile = uriToFile(context, uri)
                    imageFile?.let {
                        val multipartBody = convertFileToMultipartBody("upload_delivery_file", imageFile)
                        viewModel.uploadFile(multipartBody) }

                }

            }
        val cameraLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val image = result.data?.extras?.get("data") as Bitmap?
                    image?.let {
                        val file = bitmapToFile(context, it)
                        file?.let { file ->
                            val multipartBody = convertFileToMultipartBody("upload_delivery_file", file)
                            viewModel.uploadFile(multipartBody)
                        }
                    }

                }
            }

        val stroke = Stroke(width = 5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Column(modifier = Modifier
                .size(155.dp)
                .background(
                    color = colorResource(id = color.white),
                    shape = RoundedCornerShape(10.dp)
                )
                .drawBehind {
                    drawRoundRect(
                        color = Color.Green,
                        style = stroke,
                        cornerRadius = CornerRadius(10f)
                    )
                }
                .clickable {
                    mImageType = 2
                    openSheetCameraGallery(onCameraClick = { cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE)) },
                        onGalleryClick = { galleryLauncher.launch("image/*") })
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = drawable.upload_ic),
                            contentDescription = ""
                        )
                        VerticalSpacer(value = 5)
                        Text(
                            "Upload",
                            color = colorResource(id = color.green),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )
                        Text(
                            "Front Side",
                            color = colorResource(id = color.gray_9D9D9D),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )

                    }

                    Image(
                        painter = rememberImagePainter(request = coil.request.ImageRequest.Builder(LocalContext.current).data(mBasicFrontUrl).build()),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                  

                }


            }

            Spacer(modifier = Modifier.padding(5.dp))
            Column(modifier = Modifier
                .size(155.dp)
                .background(
                    color = colorResource(id = color.white), shape = RoundedCornerShape(10.dp)
                )
                .drawBehind {
                    drawRoundRect(
                        color = Color.Green, style = stroke, cornerRadius = CornerRadius(10f)
                    )
                }
                .clickable {
                    mImageType = 3
                    openSheetCameraGallery(onCameraClick = { cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE)) },
                        onGalleryClick = { galleryLauncher.launch("image/*") })
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = drawable.upload_ic),
                            contentDescription = ""
                        )
                        VerticalSpacer(value = 5)
                        Text(
                            "Upload",
                            color = colorResource(id = color.green),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )
                        Text(
                            "Back Side",
                            color = colorResource(id = color.gray_9D9D9D),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )

                    }

                    Image(
                        painter = rememberImagePainter(request = coil.request.ImageRequest.Builder(LocalContext.current).data(mBasicBackUrl).build()),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }


            }


        }


    }



    @Composable
    fun CaptureProfilePhoto(mProfileImgUrl:String) {
        val context = LocalContext.current

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent())
        { uri ->
            uri?.let {
                val imageFile = uriToFile(context, it)
                imageFile?.let {
                    val multipartBody = convertFileToMultipartBody("upload_delivery_file", imageFile)
                    viewModel.uploadFile(multipartBody) }

            }
        }

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val image = result.data?.extras?.get("data") as Bitmap?
                image?.let {
                    val file = bitmapToFile(context, it)
                    file?.let { file ->
                        val multipartBody = convertFileToMultipartBody("upload_delivery_file", file)
                        viewModel.uploadFile(multipartBody)
                    }


                }
            }
        }

        Box(modifier = Modifier.clickable {
            if (checkPermissions())
            {
                mImageType = 1
                openSheetCameraGallery(
                    onCameraClick = {
                        cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                    },
                    onGalleryClick = { galleryLauncher.launch("image/*") },
                )

            } else {
                requestPermissions()
            }
        }) {
            when {
                mProfileImgUrl != "" -> Image(
                    painter = rememberImagePainter(
                        request = coil.request.ImageRequest.Builder(LocalContext.current)
                            .data(mProfileImgUrl).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)

                else -> Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = null
                )
            }
        }
    }



    fun bitmapToFile(context: Context, bitmap: Bitmap): File? {
        // Create a file in the cache directory
        var time = System.currentTimeMillis()
        val file = File(context.cacheDir, "img$time.jpg")
        return try {
            // Compress the bitmap and save it to the file
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun showDialog1(context: Context?)
    {
        val binding = LayoutThankYouBinding.inflate(LayoutInflater.from(context))
        val mBuilder = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
        mBuilder.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        mBuilder.setCancelable(false)
        mBuilder.show()
        binding.mOkay.setOnClickListener {
            mBuilder.dismiss()
            moveActivity(UnderReview())
            finishAffinity()

        }


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun BankDetailsUI() {
        Column {
            Text(
                text = "Enter your correct bank details, you'll receive earning in.",
                color = colorResource(id = color.black_333333),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(font.montserrat_medium)),
                textAlign = TextAlign.Start,
            )
            VerticalSpacer(value = 10)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colorResource(id = color.white_F6F6F6), shape = RoundedCornerShape(10.dp)
                    )
                    .padding(all = 16.dp)
            ) {


                var holderName by remember { mutableStateOf("") }
                var bankName by remember { mutableStateOf("") }
                var accountNumber by remember { mutableStateOf("") }
                var confirmAccountNumber by remember { mutableStateOf("") }
                var bankCode by remember { mutableStateOf("") }



                TextField(modifier = Modifier.fillMaxWidth(),
                    value = holderName,
                    onValueChange = { holderName = it },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text("Account Holder Name", color = colorResource(id = color.gray_9D9D9D))

                    })

                VerticalSpacer(value = 12)

                TextField(modifier = Modifier.fillMaxWidth(),
                    value = bankName,
                    onValueChange = { bankName = it },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text("Bank Name", color = colorResource(id = color.gray_9D9D9D))

                    })

                VerticalSpacer(value = 12)

                TextField(modifier = Modifier.fillMaxWidth(),
                    value = accountNumber,
                    onValueChange = { accountNumber = it },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text("Account Number", color = colorResource(id = color.gray_9D9D9D))

                    })

                VerticalSpacer(value = 12)

                TextField(modifier = Modifier.fillMaxWidth(),
                    value = confirmAccountNumber,
                    onValueChange = { confirmAccountNumber = it },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(
                            "Confirm Account Number", color = colorResource(id = color.gray_9D9D9D)
                        )

                    })

                VerticalSpacer(value = 12)

                TextField(modifier = Modifier.fillMaxWidth(),
                    value = bankCode,
                    onValueChange = { bankCode = it },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text("Bank Code", color = colorResource(id = color.gray_9D9D9D))

                    })

                VerticalSpacer(value = 30)

                Button(
                    onClick = {
                        if (validationBank(holderName,bankName,accountNumber,confirmAccountNumber,bankCode))
                        {
                            var requrest  = AddBankRequest(
                                accHolderName = holderName,
                                bankAccountNo = accountNumber,
                                bankCode = bankCode,
                                bankName = bankName)

                            Log.d("daya", "BankDetailsUI: $requrest")
                            viewModel.hitAddBank(token = SharedPreference.get(this@SignupScreen).accessToken, requrest)

                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = color.green)
                    )
                ) {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(font.montserrat_semibold))
                    )
                }


            }
        }
    }

    fun validationBank(holderName: String, bankName: String, accountNumber: String, confirmAccountNumber: String, bankCode: String): Boolean
    {
        if (holderName.isEmpty())
        {
          showToast("Please enter account holder name")
            return false
        }
        else if (bankName.isEmpty())
        {
            showToast("Please enter bank name")
            return false
        }
        else if (accountNumber.isEmpty())
        {
            showToast("Please enter account number")
            return false
        }
        else if (accountNumber.length < 10)
        {
            showToast("Please enter valid account number")
            return false
        }
        else if(accountNumber!=confirmAccountNumber)
        {
            showToast("Account number and confirm account number not match")
            return false
        }
        else if (confirmAccountNumber.isEmpty())
        {
            showToast("Please enter confirm account number")
            return false
        }
        else if (bankCode.isEmpty())
        {
            showToast("Please enter bank code")
            return false
        }


        return true

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun VehicleDetailsUI(viewModel:M1ViewModel) {
        var vehicleNumber by remember { mutableStateOf("") }

        val mVehicleUrl1 by viewModel.mVehicleUrl1.observeAsState(initial = "")
        val mVehicleUrl2 by viewModel.mVehicleUrl2.observeAsState(initial = "")
        val mVehicleUrl3 by viewModel.mVehicleUrl3.observeAsState(initial = "")

        Column {
            Text(
                text = "Select a vehicle type, & fill other vehicle details.",
                color = colorResource(id = color.black_333333),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(font.montserrat_medium)),
                textAlign = TextAlign.Center,
            )
            VerticalSpacer(value = 10)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        colorResource(id = color.white_F6F6F6), shape = RoundedCornerShape(10.dp)
                    )
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = "Vehicle Number",
                    color = colorResource(id = color.black_333333),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(font.montserrat_semibold)),
                    textAlign = TextAlign.Start
                )
                VerticalSpacer(value = 5)
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = vehicleNumber,
                    onValueChange = { vehicleNumber = it },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text("Vehicle Number", color = colorResource(id = color.gray_9D9D9D))

                    })
                VerticalSpacer(value = 16)
                Text(
                    text = "Registration Certification",
                    color = colorResource(id = color.black_333333),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(font.montserrat_semibold)),
                    textAlign = TextAlign.Start)
                VerticalSpacer(value = 5)
                val stroke = Stroke(width = 5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                val context = LocalContext.current
                val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent())
                { uri ->
                    uri?.let {
                        val imageFile = uriToFile(context, uri)
                        imageFile?.let {
                            val multipartBody =
                                convertFileToMultipartBody("upload_delivery_file", imageFile)
                            viewModel.uploadFile(multipartBody) }
                    }
                }

                val cameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            val image = result.data?.extras?.get("data") as Bitmap?
                            image?.let {
                                val file = bitmapToFile(context, it)
                                file?.let { file ->
                                    val multipartBody = convertFileToMultipartBody("upload_delivery_file", file)
                                    viewModel.uploadFile(multipartBody)
                                }
                            }
                        }
                    }

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color.White)
                    .drawBehind {
                        drawRoundRect(
                            color = Color.Green, style = stroke, cornerRadius = CornerRadius(10f)
                        )
                    }
                    .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        mImageType = 4
                        openSheetCameraGallery(onCameraClick = {
                            cameraLauncher.launch(
                                Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE
                                )
                            )
                        }, onGalleryClick = { galleryLauncher.launch("image/*") })

                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    if (mVehicleUrl1!="")
                    {
                        Image(painter = rememberImagePainter(request = ImageRequest.Builder(LocalContext.current).data(mVehicleUrl1).build()), contentDescription = null,modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    }


                    Image(
                        painter = painterResource(id = R.drawable.upload_ic),
                        contentDescription = null
                    )
                    VerticalSpacer(value = 5)

                    Text(
                        "Upload",
                        color = colorResource(id = color.green),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(font.montserrat_regular))
                    )

                    Text(
                        "Upload Registration Certification",
                        color = colorResource(id = color.gray_9D9D9D),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(font.montserrat_regular))
                    )
                }

                VerticalSpacer(value = 18)

                Text(
                    text = "Driving License",
                    color = colorResource(id = color.black_333333),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(font.montserrat_semibold)),
                    textAlign = TextAlign.Start,
                )

                VerticalSpacer(value = 5)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .background(Color.White)
                        .drawBehind {
                            drawRoundRect(
                                color = Color.Green,
                                style = stroke,
                                cornerRadius = CornerRadius(10f)
                            )
                        }
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            mImageType = 5

                            openSheetCameraGallery(onCameraClick = {
                                cameraLauncher.launch(
                                    Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE
                                    )
                                )
                            }, onGalleryClick = { galleryLauncher.launch("image/*") })

                        },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        if (mVehicleUrl2!="")
                        {
                            Image(painter = rememberImagePainter(data = ImageRequest.Builder(context).data(mVehicleUrl2).build()), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        }



                        Image(
                            painter = painterResource(id = drawable.upload_ic),
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        VerticalSpacer(value = 5)

                        Text(
                            "Upload",
                            color = colorResource(id = color.green),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )

                        Text(
                            "Front Side",
                            color = colorResource(id = color.gray_9D9D9D),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )
                    }


                    Column(modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .background(Color.White)
                        .drawBehind {
                            drawRoundRect(
                                color = Color.Green,
                                style = stroke,
                                cornerRadius = CornerRadius(10f)
                            )
                        }
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            mImageType = 6
                            openSheetCameraGallery(onCameraClick = {
                                cameraLauncher.launch(
                                    Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE
                                    )
                                )
                            }, onGalleryClick = { galleryLauncher.launch("image/*") })

                        },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        if (mVehicleUrl3!="")
                        {
                            Image(painter = rememberImagePainter(request = ImageRequest.Builder(LocalContext.current).data(mVehicleUrl3).build()), contentDescription = null,modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        }


                        Image(
                            painter = painterResource(id = drawable.upload_ic),
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        VerticalSpacer(value = 5)

                        Text(
                            "Upload",
                            color = colorResource(id = color.green),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )

                        Text(
                            "Back Side",
                            color = colorResource(id = color.gray_9D9D9D),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(font.montserrat_regular))
                        )
                    }

                }
                VerticalSpacer(value = 18)
                Button(
                    onClick = {
                        if (vehicleValidation(
                                vehicleNumber = vehicleNumber,
                                vehicleDoc1 = mVehicleUrl1,
                                vehicleDoc2 = mVehicleUrl2,
                                vehicleDoc3= mVehicleUrl3))
                        {
                            val userRequest = AddVehicleRequest(
                                vechileName = vehicleNumber,
                                certificate = mVehicleUrl1,
                                drivingLicenseFront = mVehicleUrl2,
                                drivingLicenseBack = mVehicleUrl3)

                            Log.d("daya", "VehicleDetailsUI: $userRequest")

                            viewModel.hitAddVehicle(token = SharedPreference.get(this@SignupScreen).accessToken, userRequest)

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = color.green)))
                {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(font.montserrat_semibold))
                    )
                }


            }


        }
    }

    fun getIntentDat() {
        if (intent.hasExtra("basic"))
        {
            viewModel.updateSignupType(1)
        }
        if (intent.hasExtra("vehicle"))
        {
            viewModel.updateSignupType(2)

        }
        if (intent.hasExtra("bank"))
        {
            viewModel.updateSignupType(3)

        }
    }

    private fun vehicleValidation(
        vehicleNumber: String, vehicleDoc1: String, vehicleDoc2: String, vehicleDoc3: String
    ): Boolean {
        if (vehicleNumber.isEmpty()) {
            showToast("Please enter vehicle number")
            return false
        } else if (vehicleDoc1.isEmpty()) {
            showToast("Please upload vehicle certificate")
            return false
        } else if (vehicleDoc2.isEmpty()) {
            showToast("Please upload vehicle document front")
            return false
        } else if (vehicleDoc3.isEmpty()) {
            showToast("Please upload vehicle document back")
            return false
        }
        return true
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun selectLanguageLayout(
        mProfileImgUrl: String,
        mBasicFrontUrl: String,
        mBasicBackUrl: String,
        name: String,
        phone: String,
        email: String,
        gender: String,
        dob: String,
        dobTimeStamp: String,
        password: String,
        onPasswordChange: (String) -> Unit,
        confrimPassword: String,
        onConfrimPasswordChange: (String) -> Unit,
        countryCode: String,
        isAgreeTerm:Boolean,
        onIsAgreeTermChange: (Boolean) -> Unit,
        selectLang: String,
        mSelectedLanguageType: (String) -> Unit,
        onChangeLanguage: (String) -> Unit,
        onIsSmokeChange1: (Boolean) -> Unit,

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = color.white_F6F6F6),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Select Language",
                    color = colorResource(id = color.black_333333),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(font.montserrat_semibold))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(top = 10.dp)
                        .background(
                            color = colorResource(id = color.white),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() })
                        {
                            selectLanguageSheet(
                                mSelectedLanguageType = { mSelectedLanguageType(it) },
                                mSelectedLanguage = { onChangeLanguage(it) },
                            )
                        }
                        .padding(horizontal = 16.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                    )
                {
                    Text(selectLang, fontFamily = FontFamily(Font(font.montserrat_regular)), fontSize = 14.sp, color = colorResource(id = color.black_333333))

                    Image(
                        painter = painterResource(id = drawable.arrow_drop_down),
                        contentDescription = "")


                }
                VerticalSpacer(12)
                Text(
                    "Upload ID Proof",
                    color = colorResource(id = color.black_333333),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(font.montserrat_semibold))
                )
                VerticalSpacer(12)
                UploadFrontBackDocument(
                    mBasicFrontUrl = mBasicFrontUrl, mBasicBackUrl = mBasicBackUrl)
                VerticalSpacer(12)
                TextField(value = password,
                    onValueChange = {
                        onPasswordChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter Create Password", color = colorResource(id = color.gray_9D9D9D))
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = colorResource(id = color.black_161616)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                )
                VerticalSpacer(value = 12)
                TextField(value = confrimPassword,
                    onValueChange = {
                        onConfrimPasswordChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Confirm Password", color = colorResource(id = color.gray_9D9D9D))
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = colorResource(id = color.black_161616)
                    ),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Do you Smoke?",
                        color = colorResource(id = color.black_161616),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(font.montserrat_semibold))
                    )
                    ToggleSwitch(onIsSmokeChange={onIsSmokeChange1(it)})

                }
                CustomCheckbox(onIsAgreeTermChange = { onIsAgreeTermChange(it) })
                VerticalSpacer(20)
                Button(
                    onClick = {
                        if (basicValidation(
                                mProfileImgUrl = mProfileImgUrl,
                                mBasicFrontUrl = mBasicFrontUrl,
                                mBasicBackUrl = mBasicBackUrl,
                                name = name,
                                phone = phone,
                                email = email,
                                gender = gender,
                                dobTimeStamp = dobTimeStamp,
                                selectLang = selectLang,
                                password = password,
                                confrimPassword = confrimPassword,
                                isTermCheck = isAgreeTerm)
                        ) {
                            //BasicClick
                            var requrest = RegisterRequest(
                                name = name,
                                profileImage = mProfileImgUrl,
                                countryCode = countryCode,
                                email = email,
                                language = selectLang,
                                dob = dobTimeStamp,
                                mobileNumber = phone,
                                deviceToken = "a",
                                deviceType = 1,
                                lat = 0.0,
                                long = 0.0,
                                isSmoke = 0,
                                gender = 0,
                                password = password,
                                idProofFront = mBasicFrontUrl,
                                idProofBack = mBasicBackUrl
                            )

                            Log.d("daya", "Data Request: $requrest")
                            viewModel.hitRegister(requrest)


                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = color.green)
                    )
                ) {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(font.montserrat_semibold))
                    )
                }

            }

        }
    }

    private fun basicValidation(
        mProfileImgUrl: String,
        mBasicFrontUrl: String,
        mBasicBackUrl: String,
        name: String,
        phone: String,
        email: String,
        gender: String,
        dobTimeStamp: String,
        selectLang: String,
        password: String,
        confrimPassword: String,
        isTermCheck:Boolean,
    ): Boolean {

        if (mProfileImgUrl.isEmpty())
        {
            showToast("Please upload Profile Picture in PNG, JPG and JPEG format")
            return false
        }
       else  if (name.isEmpty()) {
            showToast("Please enter full name")
            return false
        } else if (phone.isEmpty()) {
            showToast("Please enter mobile number")
            return false

        }else if(phone.length !=10 && phone.length<=15){
            showToast("Please enter valid mobile number")}
        else if (email.isEmpty()) {
            showToast("Please enter email")
            return false

        }
         else if (!email.matches(Constant.EMAIL_PATTERN.toRegex()))
         {
            showToast("Please enter valid Email Address")
            return false

        }
        else if (gender=="Select Gender") {
            showToast("Please select Gender")
            return false

        } else if (dobTimeStamp=="") {
            showToast("Please enter DOB")
            return false

        } else if (selectLang=="Select Language") {
            showToast("Please select Language")
            return false

        } else if (mBasicFrontUrl=="") {
            showToast("Please upload Front Side ID Proof")
            return false
        } else if (mBasicBackUrl=="") {
            showToast("Please upload Back Side ID Proof")
            return false
        }
        else if (password.isEmpty()) {
            showToast("Please enter your password")
            return false
        }
          else if (!password.matches(Constant.PASSWORD_PATTERN.toRegex()))
          {
            showToast("Password must be 8-15 chars with lowercase, uppercase, number, special character.")
            return false
        }


        else if (confrimPassword.isEmpty()) {
            showToast("Please enter confirm password")
            return false
        } else if (password != confrimPassword) {
            showToast("Password and Confirm Password should be same")
            return false
        }
       else if (!isTermCheck)
        {
            showToast("Please accept Term and conditions")
            return false
        }

        return true
    }


    fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver ?: return null
        var time = System.currentTimeMillis()
        val tempFile = File(context.cacheDir, "img$time")
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return tempFile
    }

    fun convertFileToMultipartBody(partName: String, file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }




    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun SignupScreenContent(viewModel: M1ViewModel) {
        val context = LocalContext.current
        val signupType by viewModel.signupType.observeAsState(initial = 1)
        val mAddVehicleResp by viewModel.mAddVehicleResp.observeAsState()


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            TitleLayout()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp)
                    .background(Color.White)
            ) {
                mAddVehicleResp?.message?.let {
                    Text(
                        text = it,
                        color = colorResource(id = color.gray_9D9D9D),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(font.montserrat_regular)),
                        textAlign = TextAlign.Start
                    )
                }

                Text(text = "Hey It's so simple to create a new account just in 3 steps.", textAlign = TextAlign.Center, fontSize = 12.sp,color= colorResource(
                    id = color.gray_9D9D9D
                ).copy(alpha = 0.5f))
                progressLayout(signupType)
                VerticalSpacer(value = 5)
                when (signupType) {
                    1 -> basicDetailsUI(viewModel)
                    2 -> VehicleDetailsUI(viewModel)
                    3 -> BankDetailsUI()
                    else -> {
                        basicDetailsUI(viewModel)
                    }
                }

            }

        }
    }




    fun openSheetCameraGallery(
        onCameraClick: () -> Unit,
        onGalleryClick: () -> Unit)
    {
        val dialog = BottomSheetDialog(this)
        val binding = LayoutCameraGalleryBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.show()
        binding.mCamera.setOnClickListener {
            onCameraClick()
            dialog.dismiss()
        }
        binding.mGallery.setOnClickListener {
            onGalleryClick()
            dialog.dismiss()
        }
    }

        fun selectGenderSheet(
        onGenderChangeValue: (String) -> Unit,
        onMaleClick: (String) -> Unit,
        onFemaleClick: (String) -> Unit,
        onOtherClick: (String) -> Unit)
    {
        val dialog = BottomSheetDialog(this)
        val binding = LayoutSelectGenderBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.show()
        binding.mMale.setOnClickListener {
            onMaleClick("Male")
            onGenderChangeValue("0")
            dialog.dismiss()
        }
        binding.mFemale.setOnClickListener {
            onFemaleClick("Female")
            onGenderChangeValue("1")

            dialog.dismiss()
        }
           binding.mOther.setOnClickListener {
               onOtherClick("Other")
               onGenderChangeValue("2")

               dialog.dismiss()
        }


    }





      fun selectLanguageSheet(
          mSelectedLanguageType: (String) -> Unit,
          mSelectedLanguage: (String) -> Unit)
    {
        val dialog = BottomSheetDialog(this)
        val binding = LayoutSelectLanguageBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.show()
        binding.mArabic.setOnClickListener {
            mSelectedLanguageType("ar")
            mSelectedLanguage("Arabic")
            dialog.dismiss()
        }
        binding.mEnglish.setOnClickListener {
            mSelectedLanguageType("en")
            mSelectedLanguage("English")
            dialog.dismiss()
        }



    }














    @Composable
    private fun basicDetailsUI(viewModel: M1ViewModel) {
        val scrollState = rememberScrollState()
        var fullName by remember { mutableStateOf("") }
        var mobileNumber by remember { mutableStateOf("") }
        var countryCode by remember { mutableStateOf("") }
        var emailAddress by remember { mutableStateOf("") }
        var selectGender by remember { mutableStateOf("Select Gender") }
        var selectGenderType by remember { mutableStateOf("0") }
        var enterDOB by remember { mutableStateOf("Enter DOB") }
        var dobTimeStamp by remember { mutableStateOf("") }
        var selectLangType by remember { mutableStateOf("") }
        var selectLang by remember { mutableStateOf("Select Language")}
        var createPassword by remember { mutableStateOf("") }
        var confrimPassword by remember { mutableStateOf("") }
        var isSmoke by remember { mutableStateOf(false) }
        var isAgreeTerm by remember { mutableStateOf(false) }

        val mProfileImgUrl by viewModel.mProfileImgUrl.observeAsState(initial = "")
        val mBasicFrontUrl by viewModel.mBasicFrontUrl.observeAsState(initial = "")
        val mBasicBackUrl by viewModel.mBasicBackUrl.observeAsState(initial = "")


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CaptureProfilePhoto(mProfileImgUrl)
                Image(
                    painter = painterResource(id = drawable.add_upload),
                    contentDescription = "",
                    modifier = Modifier.offset(y = -15.dp)
                )
            }

            VerticalSpacer(value = 20)
            //fillDetailsLayout(fullName,onFullNameChange = { fullName = it })
            fillDetailsLayout(
                fullName = fullName,
                onFullNameChange = { fullName = it },
                mobileNumber = mobileNumber,
                onMobileNumberChange = { mobileNumber = it },
                emailAddress = emailAddress,
                onEmailAddressChange = { emailAddress = it },
                selectGender = selectGender,
                onGenderChange = { selectGender = it },
                onGenderChangeType = { selectGenderType = it },
                enterDOB = enterDOB,
                onDobChange = { enterDOB = it },
                selectLang = selectLang,
                onLangChange = { selectLang = it },
                uploadFrontDoc = mBasicFrontUrl,
                onUploadFrontDocChange = {  },
                uploadBacDoc = mBasicBackUrl,
                onUploadBacDocChange = {  },
                createPassword = createPassword,
                onCreatePasswordChange = { createPassword = it },
                confrimPassword = confrimPassword,
                onConfrimPasswordChange = { confrimPassword = it },
                isSmoke = isSmoke,
                onIsSmokeChange = { isSmoke = it },
                isAgreeTerm = isAgreeTerm,
                onIsAgreeTermChange = { isAgreeTerm = it },
                onCountryCodeChange = { countryCode = it },
                onDobChangeTimeStamp = { dobTimeStamp = it },


            )

            VerticalSpacer(value = 20)
            selectLanguageLayout(
                mProfileImgUrl = mProfileImgUrl,
                mBasicFrontUrl = mBasicFrontUrl,
                mBasicBackUrl = mBasicBackUrl,
                name = fullName,
                phone = mobileNumber,
                email = emailAddress,
                gender = selectGender,
                dob = enterDOB,
                password = createPassword,
                confrimPassword = confrimPassword,
                onPasswordChange = { createPassword = it },
                onConfrimPasswordChange = { confrimPassword = it },
                countryCode = countryCode,
                isAgreeTerm = isAgreeTerm,
                onIsAgreeTermChange = { isAgreeTerm = it },
                selectLang = selectLang,
                mSelectedLanguageType = { selectLangType = it },
                onChangeLanguage = { selectLang = it },
                onIsSmokeChange1 = { isSmoke = it }, dobTimeStamp = dobTimeStamp,

            )
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES
                ), 111
            )
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 111
            )
        }
    }


    @Composable
    fun progressLayout(signupType:Int) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 0.dp, start = 25.dp, end = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Box(
                    modifier = Modifier

                        .size(30.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = color.green))

                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(2.dp)
                        .background(
                            if (signupType == 1) {
                                colorResource(id = color.gray_dc)
                            } else {
                                colorResource(id = color.green)
                            }
                        )
                )



                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(
                            if (signupType == 1) {
                                colorResource(id = color.gray_dc)
                            } else {
                                colorResource(id = color.green)
                            }
                        )

                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(2.dp)
                        .background(
                            if (signupType == 1 || signupType == 2) {
                                colorResource(id = color.gray_dc)
                            } else {
                                colorResource(id = color.green)
                            }
                        )

                )


                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(
                            if (signupType == 1 || signupType == 2) {
                                colorResource(id = color.gray_dc)
                            } else {
                                colorResource(id = color.green)
                            }
                        )

                )


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Basic Details",
                    color = colorResource(id = color.black_333333),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(font.montserrat_medium)),
                    textAlign = TextAlign.Center,
                )
                Text(

                    text = "Vehicle Details",
                    color = colorResource(id = color.black_333333),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(font.montserrat_medium)),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Bank Details",
                    color = colorResource(id = color.black_333333),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(font.montserrat_medium)),
                    textAlign = TextAlign.Center,
                )


            }


        }


    }


    @Composable
    fun CustomCheckbox(onIsAgreeTermChange: (Boolean) -> Unit)
    {
        var isChecked by remember { mutableStateOf(false) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(modifier = Modifier
                .clip(CircleShape)
                .size(20.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    isChecked = !isChecked
                    if (isChecked == true) {
                        onIsAgreeTermChange(true)
                    } else {
                        onIsAgreeTermChange(false)
                    }
                },
                painter = if (isChecked) painterResource(id = drawable.checked_mark) else painterResource(
                    id = drawable.check_mark
                ),
                contentDescription = if (isChecked) "Checked" else "Unchecked"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "I accept the terms & conditions",
                fontSize = 16.sp,
                color = colorResource(id = color.gray_9D9D9D),
            )
        }
    }

    @Composable
    fun ToggleSwitch(onIsSmokeChange: (Boolean) -> Unit) {
        var isChecked by remember { mutableStateOf(false) }
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                if (isChecked == true) {
                    onIsSmokeChange(true)
                } else {
                    onIsSmokeChange(false)
                }

                              },
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorResource(id = color.thumb_color),
                checkedTrackColor = colorResource(id = color.thumb_bg),
                uncheckedThumbColor = Color(0xFFBDBDBD),
                uncheckedTrackColor = Color(0xFFE0E0E0)
            ),
        )
    }

    fun mDateToTimestamp(value:String):Long
    {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val mDate = sdf.parse(value) as Date
        return mDate.time
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun fillDetailsLayout(
        fullName: String,
        onFullNameChange: (String) -> Unit,
        mobileNumber: String,
        onMobileNumberChange: (String) -> Unit,
        emailAddress: String,
        onEmailAddressChange: (String) -> Unit,
        selectGender: String,
        onGenderChange: (String) -> Unit,
        onGenderChangeType: (String) -> Unit,
        enterDOB: String,
        onDobChange: (String) -> Unit,
        onDobChangeTimeStamp: (String) -> Unit,
        selectLang: String,
        onLangChange: (String) -> Unit,
        uploadFrontDoc: String,
        onUploadFrontDocChange: (String) -> Unit,
        uploadBacDoc: String,
        onUploadBacDocChange: (String) -> Unit,
        createPassword: String,
        onCreatePasswordChange: (String) -> Unit,
        confrimPassword: String,
        onConfrimPasswordChange: (String) -> Unit,
        isSmoke: Boolean,
        onIsSmokeChange: (Boolean) -> Unit,
        isAgreeTerm: Boolean,
        onIsAgreeTermChange: (Boolean) -> Unit,
        onCountryCodeChange:(String)->Unit,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = color.white_F6F6F6),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = fullName,
                    onValueChange = onFullNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    placeholder = {
                        Text(
                            "Enter Full Name",
                            fontFamily = FontFamily(Font(font.montserrat_regular)),
                            fontSize = 14.sp,
                            color = colorResource(id = color.gray_9D9D9D),
                        )
                    },

                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = colorResource(id = color.black_333333)
                    ),

                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                )
                VerticalSpacer(12)

                Row(
                    modifier = Modifier.background(
                        Color.Red, shape = RoundedCornerShape(10.dp)
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextField(value = mobileNumber,
                        onValueChange = onMobileNumberChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        placeholder = {
                            Text(
                                "Enter Mobile Number",
                                fontFamily = FontFamily(Font(font.montserrat_regular)),
                                fontSize = 14.sp,
                                color = colorResource(id = color.gray_9D9D9D)
                            )

                        },
                        textStyle = TextStyle(
                            fontFamily = FontFamily(Font(font.montserrat_regular)),
                            fontSize = 14.sp,
                            color = colorResource(id = color.black_333333)
                        ),

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
                                        pickedCountry = {onCountryCodeChange(it.countryCode) },
                                        countryCodeTextColorAndIconColor = colorResource(id = color.black_161616),
                                        trailingIconComposable = {},
                                        isCircleShapeFlag = false,
                                        isCountryFlagVisible = false
                                    )
                                    VerticalDivider(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .padding(vertical = 8.dp),
                                        color = colorResource(id = color.line_color)
                                    )

                                }

                            }


                        })
                }
                VerticalSpacer(12)
                TextField(
                    value = emailAddress,
                    onValueChange = onEmailAddressChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    placeholder = {
                        Text(
                            "Enter Email Addres",
                            fontFamily = FontFamily(Font(font.montserrat_regular)),
                            fontSize = 14.sp,
                            color = colorResource(id = color.gray_9D9D9D)
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = colorResource(id = color.black_333333)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                )
                VerticalSpacer(12)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 16.dp)
                        .clickable {
                            selectGenderSheet(
                                onGenderChangeValue = { onGenderChangeType(it) },
                                onMaleClick = { onGenderChange(it) },
                                onFemaleClick = { onGenderChange(it) },
                                onOtherClick = { onGenderChange(it) })
                        }
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        selectGender,
                        fontFamily = FontFamily(Font(font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = colorResource(id = color.black_333333)
                    )
                    Image(
                        painter = painterResource(id = drawable.arrow_drop_down),
                        contentDescription = "",
                        modifier = Modifier.padding(end = 10.dp)
                    )


                }
                VerticalSpacer(12)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    val context = LocalContext.current

                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)


                    val datePickerDialog = DatePickerDialog(
                        context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                            onDobChange("$dayOfMonth/${month + 1}/$year")
                            if (dayOfMonth < 10)
                            {
                                if (month < 10)
                                {
                                    onDobChange("0$dayOfMonth/0${month + 1}/$year")
                                    val dobTimeStamp = mDateToTimestamp("0$dayOfMonth/0${month + 1}/$year")
                                    onDobChangeTimeStamp(dobTimeStamp.toString())
                                }
                                else {
                                    onDobChange("0$dayOfMonth/${month + 1}/$year")
                                    val dobTimeStamp = mDateToTimestamp("0$dayOfMonth/${month + 1}/$year")
                                    onDobChangeTimeStamp(dobTimeStamp.toString())
                                }
                            }
                            else{
                                if (month < 10)
                                {
                                    onDobChange("$dayOfMonth/0${month + 1}/$year")
                                    val dobTimeStamp = mDateToTimestamp("$dayOfMonth/0${month + 1}/$year")
                                    onDobChangeTimeStamp(dobTimeStamp.toString())
                                }
                                else {
                                    onDobChange("$dayOfMonth/${month + 1}/$year")
                                    val dobTimeStamp = mDateToTimestamp("$dayOfMonth/${month + 1}/$year")
                                    onDobChangeTimeStamp(dobTimeStamp.toString())
                                }
                            }


                        }, year, month, day
                    )
                    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

                    DatePickerText(enterDOB,datePickerDialog)
                    Image(
                        painter = painterResource(id = drawable.calendar),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                datePickerDialog.show()

                            }
                    )


                }



                VerticalSpacer(12)


            }

        }
    }


    @Composable
    fun DatePickerText(enterDOB:String,datePickerDialog:DatePickerDialog) {
        Text(text = enterDOB, modifier = Modifier
            .clickable {
                datePickerDialog.show()
            }
            .padding(top = 16.dp, bottom = 16.dp, start = 0.dp, end = 16.dp), style = androidx.compose.ui.text.TextStyle(
            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
            fontSize = 14.sp,
            color = colorResource(id = R.color.black_161616)
        ))
    }

    @Composable
    fun TitleLayout() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(colorResource(id = color.status_bar_gray))
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = drawable.back_button),
                contentDescription = "",
                Modifier.clickable {
                    onBackPressed()
                })
            Text(
                text = "Sign Up",
                color = colorResource(id = color.black_333333),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(font.montserrat_semibold)),
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(drawable.back_button),
                contentDescription = "",
                colorFilter = ColorFilter.tint(colorResource(id = color.status_bar_gray))
            )
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
}





