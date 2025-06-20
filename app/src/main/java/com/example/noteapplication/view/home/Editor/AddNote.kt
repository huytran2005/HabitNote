package com.example.noteapplication.view.home.Editor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary
import  androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.Model.Note
import com.example.noteapplication.ViewModel.NoteViewModel
import com.example.noteapplication.ui.theme.text_color

@Composable
fun TitleAddNote(title:String="Add Note"
                 ,navController: NavController
                 ,navToHome: () -> Unit={navController.navigate(AppDestinations.FirstHome)}
){
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    , horizontalArrangement =   Arrangement.Absolute.Center,) {
        Image(imageVector = (Icons.Default.ArrowBack),
            contentDescription = null,
            colorFilter = tint(primary),
            modifier = Modifier.size(30.dp).clickable{navToHome()})
        Text(text = title
            , fontSize = 24.sp
            , fontWeight = FontWeight.Bold
            , modifier = Modifier.padding(start = 24.dp)
            , color = text_color.value
        )
        Spacer(modifier = Modifier.weight(1f))
}}
@Composable
fun ContentAddNote(title: MutableState<String>,
                   content: MutableState<String>){
    Column {
        OutlinedTextField(
            value = title.value,
            onValueChange = {title.value = it},
            placeholder = { Text("Title",
                fontSize = 48.sp,
                fontWeight = FontWeight.W400,
                color = text_color.value) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent
                , focusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 48.sp,
                color = text_color.value
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = content.value,
            onValueChange = {content.value=it},
            placeholder = { Text("Type something...", fontSize = 24.sp, color = text_color.value) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 23.sp,
                color = text_color.value
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
fun AddNoteScreen(paddingValues: PaddingValues=PaddingValues()
                  ,navController: NavController = rememberNavController()){
    var title = remember { mutableStateOf("") }
    var content = remember { mutableStateOf("") }
    val viewmodel: NoteViewModel = viewModel()
    val context= LocalContext.current

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues = paddingValues)
        .background(bg_color.value),) {
        TitleAddNote(navController = navController)
        ContentAddNote(title,content)
    }
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 36.dp, end = 12.dp),
        contentAlignment = Alignment.BottomEnd){
        ComfirmContent(onClick = {
            if(title.value.isNotEmpty() and content.value.isNotEmpty()){
            viewmodel.saveNote(
                Note(title = title.value,
                    content = content.value))
            Toast.makeText(context,"Save Success",Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            }
            else{
                Toast.makeText(context,"Please fill all fields",Toast.LENGTH_SHORT).show()
            }

        }
        )
    }
}
@Composable
fun ComfirmContent(onClick:()-> Unit={}){
    Box(modifier = Modifier
        .clip(shape = CircleShape)
        .background(primary)
        .size(60.dp)
        .clickable{onClick()}
        , contentAlignment = Alignment.Center) {
        Icon(Icons.Filled.Check, null)
    }
}
