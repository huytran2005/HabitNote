package com.example.noteapplication.view.loginAccount.register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapplication.ViewModel.AuthState
import com.example.noteapplication.ViewModel.AuthViewModel
import com.example.noteapplication.ViewModel.UserViewModel
import com.example.noteapplication.ui.theme.primary
import com.example.noteapplication.view.loginAccount.login.LoadingScreen

@Composable
fun CreateAccountButton(displayname: MutableState<String>,
                        email: MutableState<String>,
                        password: MutableState<String>,
                        comfirmPassWord: MutableState<String>,
                        viewModelAuth: AuthViewModel=viewModel(),
                        viewModelUser: UserViewModel=viewModel(),
                        context: Context,
                        navToHome:()->Unit={}){
    val authState by viewModelAuth.authStateEmail.collectAsState()

    Button(

        onClick = {
            if (displayname.value.isNotEmpty() && email.value.isNotEmpty()
                && password.value.isNotEmpty() && comfirmPassWord.value.isNotEmpty()){
                if (password.value == comfirmPassWord.value) {
                    viewModelAuth.register(email.value, password.value,
                        onSuccess = {
                            viewModelUser.saveUser(displayname.value) },
                        onFailure = { errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show() })
                }
                else { Toast.makeText(context, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show() }
            }
            else{ Toast.makeText(context,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show() }
        },
        colors = ButtonDefaults.buttonColors(primary),
        modifier = Modifier.fillMaxWidth().height(52.dp)
    ) { Text("CREATE ACCOUNT") }
    when(authState){
        is AuthState.Success -> {
            navToHome()
            Toast.makeText(context, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show()
    }
        is AuthState.Error -> {
            val errorMessage = (authState as AuthState.Error).message
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
        is AuthState.Loading -> {
            LoadingScreen()
        }
        is AuthState.Idle -> {}
    }
}