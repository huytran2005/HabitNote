package com.example.noteapplication.view.home.others
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.R
import com.example.noteapplication.ViewModel.AuthViewModel
import com.example.noteapplication.ViewModel.ThemePreference
import com.example.noteapplication.ViewModel.UserViewModel
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary
import com.example.noteapplication.ui.theme.text_color
import com.example.noteapplication.view.home.Extension

import com.example.noteapplication.view.home.Footpage




@Composable
fun AlertDiaLogout(showDiaLog: MutableState<Boolean>,
                   onClick: () -> Unit={}) {
        if (showDiaLog.value) {
            AlertDialog(
                onDismissRequest = { showDiaLog.value = false },
                title = { Text("Notification") },
                text = { Text("Are you sure you want to exit the app?") },
                confirmButton = {
                    TextButton(onClick = {
                        showDiaLog.value = false
                        onClick()
                    }) {
                        Button (onClick={
                            showDiaLog.value = false
                            onClick()},
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(primary),){Text("Yes") }
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDiaLog.value = false
                    }) {
                        Button (onClick={showDiaLog.value = false },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp,Color.Black),
                            colors = ButtonDefaults.buttonColors(
                                Color.Transparent
                            ),){Text("No", color = Color.Black) }
                    }
                }
            )
        }
    }
@Composable
    fun TitleProfile(){
    Text("About Me"
        , fontSize = 36.sp
        , fontWeight = FontWeight.Bold
        , color = text_color.value
        )
}
@Composable
fun AvataProfile(userViewModel: UserViewModel = viewModel()){
    val displayname = userViewModel.displayName.value
    Row(modifier = Modifier.padding(20.dp)) {
        Box(contentAlignment = Alignment.BottomEnd){
            Image(painter = painterResource(R.drawable.bg_avata),
                contentDescription = null,
                modifier = Modifier.size(100.dp).border(2.dp, color = Color.Black, CircleShape),
                )
            Image(painter = painterResource(R.drawable.bt_fix),
                contentDescription = null,
                modifier = Modifier.size(40.dp))
        }
        Column {
        Text("Name:  $displayname", color = text_color.value)
        }
    }
}
@Composable
fun DarkModeEx(ex_ic: ImageVector, content:String
                        , onTrue:()-> Unit={}
                        , check: MutableState<Boolean>
                        ,onFalse:()-> Unit={}
                        ,onTogle:()-> Unit={}
                      ){
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)){

        Icon(imageVector = ex_ic,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = text_color.value )
        Text(content,
            modifier = Modifier.padding(start = 20.dp),
            fontSize = 15.sp,
            color = text_color.value
            )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = check.value
            ,onCheckedChange = {
                check.value=it
                if (check.value) onTrue() else onFalse()
                onTogle()               },
            modifier = Modifier.scale(0.7f),
        )
    }
}

@Composable
fun Ex(
    ex_ic: ImageVector
    , content:String,
    onClick: () -> Unit={}, ){
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(10.dp).clickable{onClick()},){

        Icon(imageVector = ex_ic,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = text_color.value)
        Text(content, modifier = Modifier.padding(start = 20.dp), fontSize = 15.sp,
            color = text_color.value)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FootpageProfile(navController: NavController,
             navToProfile:()->Unit={navController.navigate(AppDestinations.Profile)},
             navToHelp:()->Unit={navController.navigate(AppDestinations.Help)},
             navToNotes:()->Unit={navController.navigate(AppDestinations.FirstHome)}, ){
    Row (modifier = Modifier

        .background(primary)
        .fillMaxWidth()
        .padding(12.dp)
        , horizontalArrangement = Arrangement.SpaceBetween
    ){
        Extension(Icons.Filled.NoteAlt,"Notes", nav = navToNotes)
        Extension(Icons.Filled.AccountCircle,"Me", nav = navToProfile )
        Extension(Icons.Filled.Error,"Help", nav = navToHelp)
    }
}
@Composable
fun Extensions(navController: NavController,context: Context){
    var isDarkMode = remember { mutableStateOf(ThemePreference(context).isDarkMode()) }
    Column (modifier = Modifier.background(bg_color.value)){
    DarkModeEx(
        ex_ic =
        Icons.Outlined.DarkMode
        , "Dark Mode"
        , check = isDarkMode
        , onTrue =
             {
                bg_color.value = Color.Black
                text_color.value = Color.White
            },
        onFalse =
            {
                bg_color.value = Color.White
                text_color.value = Color.Black
            }
        , onTogle = {ThemePreference(context).saveDarkMode(isDarkMode.value)}
       )

        //Reset PassWord
        Ex(
            Icons.Outlined.Lock
            , "Reset Password")
        //About Us
        Ex(
            ex_ic= Icons.Filled.Error,
            content = "About",
            onClick = {navController.navigate(AppDestinations.About)})
}}

@Composable
fun LogOut(onClick: () -> Unit={}){
    Button(onClick={onClick() }
        ,modifier = Modifier.fillMaxWidth().
    padding(top = 20.dp).height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(primary)) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        Spacer(Modifier.weight(1f))
        Image(painter = painterResource(R.drawable.ic_logout),
            contentDescription = null,
            modifier = Modifier.size(30.dp).padding(start =5.dp))
        Text("LOG OUT", color = Color.White, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        }
    }
}

@Composable

fun ProfileScreen(paddingValues: PaddingValues,navController: NavController){
    val viewModelUser: UserViewModel = viewModel()
    val viewModelAuth: AuthViewModel = viewModel()
    var showDialogout: MutableState<Boolean> = remember { mutableStateOf(false) }
    val context = LocalContext.current


    Column (horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize().padding(paddingValues).background(bg_color.value)) {
        TitleProfile()
        AvataProfile(viewModelUser)
        Spacer(modifier = Modifier.padding(20.dp))
        Extensions(navController, context)
        LogOut({
            showDialogout.value=true
        })
        AlertDiaLogout(showDiaLog = showDialogout,
            onClick = {
                viewModelAuth.logout(context = context)
                navController.navigate(AppDestinations.Splash)
                ThemePreference(context).saveDarkMode(false)
            })
        Spacer(modifier = Modifier.weight(1f))
        FootpageProfile(navController = navController)

    }
}
