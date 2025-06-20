package com.example.noteapplication.view.loginAccount.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.noteapplication.ui.theme.primary
import com.example.noteapplication.view.loginAccount.login.EmailBox

@Composable
fun RegisterEmailBox(displayName: MutableState<String>
                     ,email: MutableState<String>
                     ,passWord: MutableState<String>
                     ,comfirmPassWord: MutableState<String>) {
    //Display name
    OutlinedTextField(
        value = displayName.value, onValueChange = {displayName.value=it},
        placeholder = { Text("Display Name") },
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
        ,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "user icon",
                tint = primary
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
        shape = RoundedCornerShape(12.dp)
    )
    EmailBox(email,passWord)


    // Comfirm password
    OutlinedTextField(
        value = comfirmPassWord.value, onValueChange = {comfirmPassWord.value=it},
        placeholder = { Text("Comfirm Password") },
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
        ,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = PasswordVisualTransformation()
    )
}