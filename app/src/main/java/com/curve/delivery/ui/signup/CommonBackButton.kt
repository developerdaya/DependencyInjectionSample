package com.curve.delivery.ui.signup

// CommonComposables.kt
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar
import kotlin.reflect.KClass

@Composable
fun CommonBackButton(title: String, context: Activity,img:Int) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFF0F0F0))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
       Image(painter = painterResource(id = img), contentDescription ="",Modifier.clickable {
           context.finish()
       } )
        Spacer(modifier = Modifier.width(56.dp))
        Text(
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}



@Composable
fun CommonTextView(text:String,font: Font) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Ensures the Box fills the horizontal space
            .height(36.dp), // Sets the height of the Box
        contentAlignment = Alignment.CenterStart // Aligns the content to the start within the Box
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color(0xFF333333), // Hexadecimal color #333333
                fontSize = 14.sp,
                fontFamily = FontFamily(font),
                textAlign = TextAlign.Left // Ensures the text aligns to the left
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() // Text composable fills the maximum width
        )
    }

}

@Composable
fun CustomButton(text:String,onClick: @Composable () -> Unit) {
    Button(
        onClick = { onClick },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp)) {
        Text(text, fontSize = 18.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonProfileTextFieldToast(
    label: String,
    onClick: () -> Unit,
    hasDropdown: Boolean = false,
    icon: Painter? = null,
    dropdownIcon: Painter? = null  // New parameter for a custom dropdown icon
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var context= LocalContext.current
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        singleLine = true,
        leadingIcon = icon?.let {
            { Icon(it, contentDescription = "Leading Icon", Modifier.size(20.dp),tint = Color.Unspecified)  }
        },
        trailingIcon = if (hasDropdown) {
            dropdownIcon?.let {
                { Icon(it, contentDescription = "Dropdown", Modifier.size(20.dp),tint = Color.Unspecified) }
            } ?: {
                // Fallback to default dropdown icon if custom icon is not provided
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", Modifier.size(20.dp))
            }
        } else null,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp)
            .clickable {
                // Show a toast when the TextField is clicked
                Toast
                    .makeText(context, "TextField clicked", Toast.LENGTH_SHORT)
                    .show()
            }
    )





    Spacer(Modifier.height(8.dp))
}
@Composable
fun CommonProfileTextField1(
    label: String,
    hasDropdown: Boolean = false,
    icon: Painter? = null,
    dropdownIcon: Painter? = null
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var context= LocalContext.current
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        singleLine = true,
        leadingIcon = icon?.let {
            { Icon(it, contentDescription = "Leading Icon", Modifier.size(20.dp),tint = Color.Unspecified)  }
        },
        trailingIcon = if (hasDropdown) {
            dropdownIcon?.let {
                { Icon(it, contentDescription = "Dropdown", Modifier.size(20.dp),tint = Color.Unspecified) }
            } ?: {
                // Fallback to default dropdown icon if custom icon is not provided
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", Modifier.size(20.dp))
            }
        } else null,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp)
    )

    Spacer(Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonProfileTextFieldSpinner(
    label: String,
    selectedFirst:String,
    hasDropdown: Boolean = false,
    icon: Painter? = null,
    dropdownIcon: Painter? = null
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    /*value = (selected.first),
    onValueChange = { },
    label = {  },
    modifier = Modifier.fillMaxWidth(),
    trailingIcon =  {painterResource(id = R.drawable.down) },
    readOnly = true*/

    TextField(
        value = selectedFirst,
        onValueChange = {  },
        readOnly = true,
        label = { Text(label) },
        singleLine = true,
        leadingIcon = icon?.let {
            { Icon(it, contentDescription = "Leading Icon", Modifier.size(20.dp),tint = Color.Unspecified)  }
        },
        trailingIcon = if (hasDropdown) {
            dropdownIcon?.let {
                { Icon(it, contentDescription = "Dropdown", Modifier.size(20.dp),tint = Color.Unspecified) }
            } ?: {
                // Fallback to default dropdown icon if custom icon is not provided
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", Modifier.size(20.dp))
            }
        } else null,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp)
    )





    Spacer(Modifier.height(8.dp))
}


@Composable
fun CommonProfileTextField(font: Font,keyboardType: KeyboardType, label: String,
                            hasDropdown: Boolean = false,
                            icon: ImageVector? = null,onChange:(String)->Unit) {

    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    TextField(
        value = text,
        onValueChange = { text = it
            onChange(text)},
        placeholder = { Text(label,fontFamily = FontFamily(font), fontSize = 12.sp, color = Color(0XFF9D9D9D) )},
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),// Adjust keyboard options as needed
                singleLine = true,

        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        textStyle = TextStyle(fontFamily = FontFamily(font), fontSize = 12.sp, color = Color(0XFF9D9D9D)),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))

    )
    Spacer(Modifier.height(8.dp))



}

@Composable
fun RoundedButton(
    onClick: () -> Unit,
    buttonText: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33BD8C)),
    textColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(20.dp)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            // buttonColor= buttonColor.green,
            containerColor = Color(0xFF33BD8C),
            contentColor = textColor
        )

    ) {
        Text(
            text = buttonText,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

fun startActivity(
    context: Context,
    destinationActivity: KClass<*>
    /* extras: Intent.() -> Unit = {}*/  // Default empty lambda for when no extras are needed
) {
    val intent = Intent(context, destinationActivity.java).apply {
        // extras()
    }
    context.startActivity(intent)
}

@Composable
fun ShowDatePicker(onDateSelected:  (String) -> Unit) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Remembering the DatePickerDialog to prevent recreation on recompositions
    val datePickerDialog = remember {
        DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateSelected("$dayOfMonth/${month + 1}/$year")
        }, year, month, day)
    }
    fun showDatePicker() {
        datePickerDialog.show()
    }
    showDatePicker()
}

