package com.example.noteapplication.view.home.others

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapplication.R
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary

@Composable
fun ExHelp(title:String,content:String,ex: Int){
    Spacer(modifier = Modifier.padding(vertical = 12.dp))
    Button(onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            primary
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),){
            Image(painter = painterResource(ex)
                ,"Notes", modifier = Modifier.size(60.dp).padding(12.dp))
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
                Text(content,color = Color.Black)
            }
        }
    }
}

@Composable
fun ExsHelp(){
    var titlehelpex = "Notes"
    var contenthelpex = "Tap to view"
    var ex= R.drawable.ic_note
    ExHelp(titlehelpex,contenthelpex,ex)
    titlehelpex = "Reset Password"
    ex = R.drawable.ic_resetpassword
    ExHelp(titlehelpex,contenthelpex,ex)

}
@Composable
fun HelpScreen(paddingValues: PaddingValues, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(bg_color.value)) {
        val helptitle = "Help"
        TitleAbout(helptitle, navController = navController)

        ExsHelp()

    }

}