package com.example.noteapplication.view.loginAccount.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.noteapplication.ui.theme.primary


@Composable
fun Register(navToRegister: () -> Unit={}){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        Text("Don’t have an account?", textAlign = TextAlign.Center)
        Text(" Register",
            textAlign = TextAlign.Center,
            color = primary,
            modifier = Modifier.clickable{navToRegister()}
        )
    }
}