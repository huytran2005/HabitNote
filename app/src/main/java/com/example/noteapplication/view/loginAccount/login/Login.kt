package com.example.noteapplication.view.loginAccount.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.ViewModel.AuthViewModel
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun TitleLogin(title:String="Log In"
               ,navController: NavController
               ,navToSplash:()-> Unit={navController.navigate(AppDestinations.Splash)
               }
){
    Row(Modifier.fillMaxWidth()
        , horizontalArrangement = Arrangement.Start
        , verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Default.ArrowBack,null,
            modifier = Modifier.size(30.dp).clickable{navToSplash()},
            tint = primary
        )
        Text(title, Modifier.padding(start = 24.dp),
            fontSize = 18.sp,)
    }
}

@Composable
fun ContentLogin(navController: NavController,
                 navToFirstHome:()-> Unit={navController.navigate(AppDestinations.FirstHome)},
                 navToRegister: () -> Unit={navController.navigate(AppDestinations.Register)},
                 viewModel: AuthViewModel=viewModel()
){
    Column(horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()) {

        Column (modifier = Modifier.padding(bottom = 100.dp)){
            Text(
                "Well comeback! ", fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
            Text("Please login with your credentials")
        }

        // Đăng nhập bằng google
        val authState by viewModel.authState.collectAsState()
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn
                .getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                viewModel.resetState()
                Toast.makeText(context,
                    "Google sign-in failed: ${e.message}",
                    Toast.LENGTH_SHORT).show()


            }
        }
        var email = remember { mutableStateOf("") }
        var password = remember { mutableStateOf("") }
        //Login Button with email
        EmailBox(email,password)
        ForgotPassword()

        // Button Login with email
        EmailLoginButton(
            context = context,
            email = email,
            password = password,
            navController = navController,
        )
        //Forgot password
        GoogleLoginButton(
                launcher = launcher,
                uiState = authState,
                context = context,
                navToFirstHome = navToFirstHome
            )
        // navigation to register
        Register(navToRegister)
        }

    }

@Composable
fun LoginScreen(paddingValues: PaddingValues=PaddingValues(),
                navController: NavController){
    Column(modifier = Modifier.padding(paddingValues)
        .fillMaxSize()
    ){
       TitleLogin(navController = navController)
        ContentLogin(navController)
        }

    }


