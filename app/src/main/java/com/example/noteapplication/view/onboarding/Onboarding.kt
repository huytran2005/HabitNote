package com.example.noteapplication.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.R
import com.example.noteapplication.ui.theme.primary

@Composable
fun NavigationOnboarding(sizes:List<Int> = listOf(35,35,35),color: List<Color>){
    Row (Modifier.fillMaxWidth().padding(vertical = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
        Button(onClick = {}, modifier = Modifier.size(sizes[0].dp,16.dp),
            colors = ButtonDefaults.buttonColors(color[0])) {}
        Spacer(Modifier.padding(24.dp))
        Button(onClick = {}, modifier = Modifier.size(sizes[1].dp,16.dp),
            colors = ButtonDefaults.buttonColors(color[1])) {}
    }
}

@Composable
fun ContentOnboarding(resourse:Int,
                      title:String="Task Notes",
                      content: String="Quickly capture what’s on your mind",
                      navigationsize:List<Int> = listOf(65,35,35),
                      navigationcolor:List<Color> = listOf(primary, Color.LightGray,Color.LightGray),
                      navToLogin:()-> Unit={},
                      navToRegister:()-> Unit={},){
    Column (Modifier.fillMaxWidth()
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally){
    Image(painterResource(resourse),
        contentDescription = null, Modifier.size(340.dp))
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(content, fontSize = 18.sp)

        NavigationOnboarding(navigationsize,navigationcolor)

        //Navigation to create account
    Column (horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()){

        Button(
            onClick = navToRegister,
            colors = ButtonDefaults.buttonColors(primary),
            modifier = Modifier.fillMaxWidth().height(52.dp).shadow(24.dp)) {
            Text("CREATE ACCOUNT")
        }
        Spacer(Modifier.padding(10.dp))
        //Navigation to login
        Button(onClick = navToLogin,
            colors =  ButtonDefaults.buttonColors(Color.White),
            modifier = Modifier.fillMaxWidth().height(52.dp).shadow(24.dp)) {
            Text("LOGIN", color = primary)
        }
        }
    }
}

@Composable
fun OnboardingSceen(paddingValues: PaddingValues=PaddingValues(),navController: NavController){
        Column (modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val resourse = R.drawable.bg_onboarding
            ContentOnboarding(resourse,
                navToLogin = {navController.navigate(AppDestinations.Login)}
                ,navToRegister = {navController.navigate(AppDestinations.Register)}
            )
        }
}
@Composable
fun SwitchUI(paddingValues: PaddingValues,navController: NavController){
    val pagerState = rememberPagerState(pageCount = { 2 })
    HorizontalPager(state = pagerState) { page ->
        if (page == 0) {
            OnboardingSceen(paddingValues, navController = navController)
        } else {
            OnBoardingScreen1(paddingValues,navController = navController)
        }
    }
}

