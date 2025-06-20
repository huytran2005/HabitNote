package com.example.noteapplication.view.loginAccount.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary
import com.example.noteapplication.view.loginAccount.login.EmailBox
import com.example.noteapplication.view.loginAccount.login.TitleLogin


@Composable
fun TitleCreateAccount(navController: NavController){
    TitleLogin("Create Account", navController = navController)
}
@Composable
fun ContentRegister(navController: NavController
                             ,navToLogin:()-> Unit={navController.navigate(AppDestinations.Login)}
                             ,navToHome:()-> Unit={navController.navigate(AppDestinations.FirstHome)}
) {
    val context = LocalContext.current
    //Introduce
    Column (modifier = Modifier.padding(top = 24.dp)){
        Text("Let’s get to know you !",fontWeight = FontWeight.Bold)
        Text("Enter your details to continue")
    }
    var displayName = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var passWord = remember { mutableStateOf("") }
    var comfirmPassWord = remember { mutableStateOf("") }

    RegisterEmailBox(displayName,email,passWord,comfirmPassWord,)


    //Navigation to login
    Text("Already have an account?")
    Text(
        "Login here",
        fontWeight = FontWeight.Bold,
        color = primary,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable{navToLogin()}
    )

    //Terms of use
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
            append("By clicking the “CREATE ACCOUNT”, you agree to our")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Terms of Use")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
            append(" and ")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Privacy Policy")
        }
    }, modifier = Modifier.padding(vertical = 24.dp))
    CreateAccountButton(
        displayName
        ,email
        ,passWord
        ,comfirmPassWord
        , context = context
        , navToHome = navToHome)
}
@Composable
fun RegisterScreen(paddingValues: PaddingValues= PaddingValues()
                            ,navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),) {
        TitleCreateAccount(navController = navController)
        ContentRegister(navController)
    }

}

@Preview(showBackground = true)
@Composable
fun Ahow1wd(){
    RegisterScreen(
        navController = rememberNavController()

    )
}