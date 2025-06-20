package com.example.noteapplication.view.onboarding

import  androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.R
import com.example.noteapplication.ViewModel.ThemePreference
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary
import com.example.noteapplication.ui.theme.text_color
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun ContentSplash(){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.bg_about),
            contentDescription = null,
            modifier = Modifier.size(150.dp))
        Text("HaBIT Note", textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic)
    }
}

@Composable
fun FootSplash(){
    Text("© Copyright HABIT 2021. All rights reserved",
        textAlign = TextAlign.Center, fontSize = 14.sp)
}

@Composable
fun SplashScreen(paddingValues: PaddingValues  = PaddingValues(),navController: NavController){
    val context= LocalContext.current
    val isDarkMode = remember { ThemePreference(context).isDarkMode()}
    Column(modifier = Modifier
        .fillMaxSize()
        .background(primary)
        .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.weight(1f))
            ContentSplash()
            Spacer(modifier = Modifier.weight(1f))
            FootSplash()
    }
    LaunchedEffect(key1 = true) {

        delay(500)
        val firebaseAuth = FirebaseAuth.getInstance().currentUser
        //Remove splash screen
        navController.popBackStack()
        navController.navigate(
            //Nếu đã Đăng nhập thì vào màn Home còn không thì vào màn hình đăng nhập
            if (firebaseAuth==null){
                AppDestinations.OnBoarding}
            else{
                AppDestinations.FirstHome})
    }
    //Kiểm tra xem đang ở darkmode hay lightmode
    if(isDarkMode) {
        bg_color.value= Color.Black;text_color.value= Color.White}
    else{
        bg_color.value= Color.White ;text_color.value= Color.Black}
}
