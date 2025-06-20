package com.example.noteapplication.view.loginAccount.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.noteapplication.R
import com.example.noteapplication.ui.theme.primary


@Composable
fun FBLoginButton(navToFirstHome: () -> Unit){
    Button(onClick = navToFirstHome,
        //Navigation to FirstHome/Home after Login success by fb
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(52.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(primary)) {
        Row {
            Image(
                painterResource(R.drawable.ic_facebook),
                "facebook icon",
                modifier = Modifier.size(24.dp)
            )
            Text("Facebook", Modifier.padding(horizontal = 24.dp))
        }
    }
}