package com.example.noteapplication.view.home.others

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noteapplication.R
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary
import com.example.noteapplication.ui.theme.text_color

@Composable
fun TitleAbout(title:String="About",navController: NavController){
    Column (modifier = Modifier.
    fillMaxWidth().
    height(250.dp).
    background(primary),
        horizontalAlignment = Alignment.CenterHorizontally
        ,verticalArrangement = Arrangement.Center){
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Icon(imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = null,
                Modifier.size(30.dp)
                .clickable{navController.popBackStack()},)
            Text(text = title,
                Modifier.padding(start = 10.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,)
        }

        Image(painter = painterResource(R.drawable.bg_about),
            contentDescription = null, Modifier.size(150.dp))
    }
}
@Composable
fun InforAbout(title:String,content:String){
    Row(Modifier.padding().fillMaxWidth().padding(start = 45.dp, end = 45.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        ){
        Text(title,textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = text_color.value)
        Text(content,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            color = text_color.value)
    }
}

@Composable
fun InforsAbout(){
    Column(Modifier.padding(10.dp)) {
        val application = "Application"
        var content  = "HaBIT Note"
        InforAbout(application,content)
        val version = "Version"
        content = "V1.0.0"
        InforAbout(version,content)
        val privacyPolicy = "Privacy Policy"
        content = ""
        InforAbout(privacyPolicy,content)


    }
}

@Composable
fun AboutScreen(paddingValues: PaddingValues,navController: NavController){
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(bg_color.value),){
        TitleAbout(navController=navController)
        InforsAbout()
    }
}
@Preview
@Composable
fun Show2(){
    val navController = rememberNavController()
    AboutScreen(PaddingValues(), navController)
}

