package com.curve.delivery.ui.temp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Temp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    @Composable
    fun App() {
        var view1Visible by remember { mutableStateOf(true) }
        var view2Visible by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ButtonFun(view1Visible, view2Visible) { v1, v2 ->
                view1Visible = v1
                view2Visible = v2
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Conditionally display views based on their visibility state
            if (view1Visible) {
                View1()
            }
            else
            {
                View2()
            }

        }
    }

    @Composable
    private fun ButtonFun(
        view1Visible: Boolean,
        view2Visible: Boolean,
        onClick111: (Boolean, Boolean) -> Unit
    ) {
        Row {
            Button(onClick = { onClick111(true, false) }) {
                Text("view1Visible")
            }
            Button(onClick = { onClick111(false, true) }) {
                Text("view2Visible")
            }
        }
    }

    @Composable
    fun View1() {
        BasicText("View 1", modifier = Modifier.padding(8.dp))
    }

   @Composable
    fun View2() {
        BasicText("View 2", modifier = Modifier.padding(8.dp))
    }


}
