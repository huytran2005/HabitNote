package com.example.noteapplication.view.loginAccount.ForgotPassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noteapplication.view.loginAccount.login.TitleLogin

@Composable
fun ScreenForgotPassword(navController: NavController){
    Column (modifier = Modifier.fillMaxSize()){
        TitleLogin(navController = navController,title = "Forgot Password")
    }
}
@Preview(showBackground = true)
@Composable
fun Sho2w(){
    val navController = rememberNavController()
    ScreenForgotPassword(navController)
}