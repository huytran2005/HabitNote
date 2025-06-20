package com.example.noteapplication.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.R
import com.example.noteapplication.ui.theme.primary
@Composable
fun OnBoardingScreen1(paddingValues: PaddingValues= PaddingValues(),navController: NavController){
    Column (
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
            val resource=R.drawable.bg_onboarding1
            val title="To-dos"
            val content="List out your day-to-day tasks"
            val navigationsize = listOf(35,65,35)
            val navigationcolor = listOf(Color.LightGray,primary,Color.LightGray)
        ContentOnboarding(
            resource, title = title,
            content=content,
            navigationsize = navigationsize,
            navigationcolor=navigationcolor,
            navToLogin = {navController.navigate(AppDestinations.Login)},
            navToRegister = {navController.navigate(AppDestinations.Register)}
        )
    }
}
