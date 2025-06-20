package com.example.noteapplication.view.loginAccount.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.noteapplication.Model.signInWithGoogle
import com.example.noteapplication.R
import com.example.noteapplication.ViewModel.AuthState


@Composable
fun GoogleLoginButton(
    navToFirstHome: () -> Unit,
    uiState: AuthState,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context){
    Button(onClick = {
        //Analyse google login result
        signInWithGoogle(launcher, context =context)
    },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(52.dp).fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color("#333333".toColorInt())),
        border = BorderStroke(1.dp, Color.Black),) {

        Row {
            Image(painterResource(R.drawable.ic_google),"google icon", modifier = Modifier.size(24.dp))
            Text("Or sign in with Google", Modifier.padding(horizontal = 24.dp), color = Color.White)
        }
    }
    LaunchedEffect(uiState) {
        if (uiState is AuthState.Success) {
            navToFirstHome()
            Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
        }
        if (uiState is AuthState.Error) {
            Toast.makeText(context, "Lỗi đăng nhập google"+uiState.message, Toast.LENGTH_SHORT).show()
        }
    }
}