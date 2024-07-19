package com.curve.delivery.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.curve.delivery.R

class HomeScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HomeScreenContent() }
    }

    @Composable
    fun verticalSpacer(value: Int) {
        Spacer(modifier = Modifier.height(value.dp))
    }

    @Composable
    fun horizontalSpacer(value: Int) {
        Spacer(modifier = Modifier.width(value.dp))
    }

    @Composable
    fun textViewBold(text: String, fontSize: Int, color: Int) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
            color = colorResource(R.color.line_color)
        )
    }

    @Composable
    fun textViewSemiBold(text: String, fontSize: Int, color: Int,  modifier1: Modifier = Modifier,
                         textAlign1: TextAlign? = null) {
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


    @Composable
    fun imageViewTint(image: Int, tint: Int) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorResource(id = tint))
        )
    }


    @Composable
    @Preview(showSystemUi = true)
    fun HomeScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        {
            titleLayout()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            {
                cardLayout()
                orderLayout()
                verticalSpacer(value = 30)
                orderReceivedLayout()

            }


        }

    }

    @Composable
    private fun orderReceivedLayout() {
        Column(
            modifier = Modifier
                .fillMaxWidth().padding(bottom = 10.dp)
        )
        {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            )
            {

                Column(
                    modifier = Modifier
                        .fillMaxWidth())
                {
                    textViewMedium(
                        "You Received New Request", 14, R.color.white,
                        modifier1 = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.green))
                            .padding(14.dp),
                        textAlign1 = TextAlign.Center
                    )
                    Row(modifier = Modifier.padding(12.dp))
                    {
                        imageView(image = R.drawable.logo_symbol,modifier1 = Modifier.size(80.dp))
                        horizontalSpacer(value = 10)
                        Column {
                            textViewRegular(
                                text = "Food Order/ Grocery Order",
                                fontSize = 12,
                                color = R.color.gray_6B6B6B)
                            verticalSpacer(value = 8)
                            textViewSemiBold(
                                text = "Haldiram's Restaurant",
                                fontSize = 14,
                                color = R.color.black_333333
                            )
                            verticalSpacer(value = 4)
                            Row {
                                imageView(image = R.drawable.baseline_location_on_24)
                                horizontalSpacer(value = 5)
                                textViewRegular(
                                    text = "249 Boring Lane, California, 34832",
                                    fontSize = 12,
                                    color = R.color.gray_6B6B6B
                                )

                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        textViewSemiBold(
                            text = "Accept",
                            fontSize = 12,
                            color = R.color.green_39C166,
                            textAlign1 = TextAlign.Center,
                            modifier1 = Modifier
                                .weight(1f)
                                .background(colorResource(id = R.color.green_C8EED4))
                                .padding(15.dp)
                        )
                        textViewSemiBold(text = "Reject", fontSize = 12, color = R.color.red_D63636,
                            textAlign1 = TextAlign.Center,
                            modifier1 = Modifier.weight(1f)
                                .background(colorResource(id = R.color.red_F8DFDF))
                                .padding(15.dp)

                        )
                    }


                }
            }


        }

    }

    @Composable
    private fun orderLayout() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        {
            textViewSemiBold("Your Today's Jobs & Earnings", 16, R.color.black_333333)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                totalOrderLayout()
                totalEarnLayout()

            }

        }

    }

    @Composable
    private fun totalOrderLayout() {
        Column(
            modifier = Modifier
                .width(170.dp)
                .height(135.dp)
                .padding(top = 12.dp)
                .background(
                    shape = RoundedCornerShape(14.dp),
                    color = colorResource(id = R.color.sky_color)
                )
                .padding(16.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                textViewSemiBold(text = "13", fontSize = 36, color = R.color.black_333333)
                imageView(image = R.drawable.jbs_ern_orders)
            }
            verticalSpacer(value = 10)
            textViewSemiBold(text = "Total Orders", fontSize = 12, color = R.color.black_333333)
            verticalSpacer(value = 8)
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                textViewSemiBold(text = "View History", fontSize = 11, color = R.color.blue_003)
                horizontalSpacer(value = 2)
                imageView(image = R.drawable.baseline_arrow_forward_ios_24)
            }


        }
    }


    @Composable
    private fun totalEarnLayout() {
        Column(
            modifier = Modifier
                .width(170.dp)
                .height(135.dp)
                .padding(top = 12.dp)
                .background(
                    shape = RoundedCornerShape(14.dp),
                    color = colorResource(id = R.color.yellow_light)
                )
                .padding(16.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                textViewSemiBold(text = "$131", fontSize = 36, color = R.color.black_333333)
                imageView(image = R.drawable.jbs_ern_earned)
            }
            verticalSpacer(value = 10)
            textViewSemiBold(text = "Total Earned", fontSize = 12, color = R.color.black_333333)
            verticalSpacer(value = 8)
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                textViewSemiBold(text = "View History", fontSize = 11, color = R.color.yellow)
                horizontalSpacer(value = 2)
                imageViewTint(image = R.drawable.baseline_arrow_forward_ios_24, R.color.yellow)
            }


        }
    }


    @Composable
    fun cardLayout() {
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = -10.dp)
                .padding(top = 10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

            )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            )

            {
                imageView(R.drawable.profile_placeholder)
                verticalSpacer(value = 8)
                textViewMedium("Faizan Khan ", 16, R.color.black_05)
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    textViewMedium("Duty", 12, R.color.black_05)
                    ToggleSwitch()
                }

            }
        }
    }

    @Composable
    fun ToggleSwitch() {
        var isChecked by remember { mutableStateOf(true) }
        Switch(
            modifier = Modifier.scale(0.6f),
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorResource(id = R.color.thumb_color),
                checkedTrackColor = colorResource(id = R.color.thumb_bg),
                uncheckedThumbColor = Color(0xFFBDBDBD),
                uncheckedTrackColor = Color(0xFFE0E0E0)
            ),
        )
    }

    @Composable
    private fun titleLayout() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(10f)
                .background(Color.White)
                .padding(start = 40.dp, end = 40.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                imageView(R.drawable.lateral_menu)
                horizontalSpacer(12)
                imageView(R.drawable.location)
                horizontalSpacer(5)
                textViewMedium("XYZ Street,Dubai", 12, R.color.black_333333)
                horizontalSpacer(5)
                imageView(image = R.drawable.arrow_drop_down)
            }
            imageView(image = R.drawable.notificatons)
        }
        Card(
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = -10.dp)
                .rotate(180f)
                .padding(top = 10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        )
        {
            textViewMedium("", 12, R.color.white)
            textViewMedium("", 12, R.color.white)

        }
    }
}


