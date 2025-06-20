package com.example.noteapplication.view.loginAccount.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.ViewModel.AuthState
import com.example.noteapplication.ViewModel.AuthViewModel
import com.example.noteapplication.ui.theme.primary


@Composable
fun LoadingScreen(){
    Dialog(onDismissRequest = {}){

        Box(
            modifier = Modifier
                .padding(32.dp),
            contentAlignment = Alignment.Center,

        ) {
            CircularProgressIndicator(color = primary)
        }
    }
}

@Composable
fun EmailLoginButton(context: Context,
                     viewModel: AuthViewModel = viewModel (),
                     email: MutableState<String>,
                     password: MutableState<String>,
                     navController: NavController,
                     navToFirstHome: () -> Unit = { navController.navigate(AppDestinations.FirstHome)},
                     ){
    val authState by viewModel.authStateEmail.collectAsState()
    Button(onClick = {
        if (email.value.isEmpty() || password.value.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        } else {

            viewModel.login(email.value, password.value)
        }
    },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(primary),
        shape = RoundedCornerShape(12.dp),

    )
    {Text("LOG IN") }
    Spacer(modifier = Modifier.padding(12.dp))
    when(authState) {
        is AuthState.Success -> {
            navToFirstHome()
        }
        is AuthState.Error -> {
            val errorMessage = (authState as AuthState.Error).message
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
        is AuthState.Loading -> {
            LoadingScreen()
        }
        is AuthState.Idle ->{}
    }
}

@Composable
fun ForgotPassword(){
    Text("Forgot Password?", fontSize = 14.sp
        , textAlign = TextAlign.End
        , modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp))
}
@Composable
fun EmailBox(email: MutableState<String>,
             password: MutableState<String>){
    //Email entry
    OutlinedTextField(
        value = email.value,
        onValueChange = {email.value = it},
        placeholder = {Text("Email Address")},
        leadingIcon = { Icon(imageVector = Icons.Default.MailOutline
            , null,
            tint = primary) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
        ),
        modifier = Modifier
            .fillMaxWidth().padding(12.dp)
            ,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),


        )
    //PassWord

    OutlinedTextField(value = password.value, onValueChange = {password.value=it},
        leadingIcon = { Icon(imageVector = Icons.Default.Lock
            , null
            ,tint = primary) },
        placeholder = {Text("Password")},
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
        ,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        visualTransformation = PasswordVisualTransformation()
    )
}