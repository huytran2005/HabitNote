package com.example.noteapplication.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.R
import com.example.noteapplication.ViewModel.NoteViewModel
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.primary
import com.example.noteapplication.ui.theme.text_color
import kotlinx.coroutines.launch


@Composable
fun AddNoteButton(diaLogState: MutableState<Boolean>){
    Button (modifier = Modifier.size(70.dp).shadow(0.dp),
        onClick = {diaLogState.value=true},
        colors = ButtonDefaults.buttonColors(text_color.value)){
        Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "addnote",
            tint = bg_color.value,
            modifier = Modifier.size(30.dp)
            )
    }
}

@Composable
fun SearchBox(search: MutableState<String>,
              noteViewModel: NoteViewModel= viewModel()
){
    OutlinedTextField(
        value = search.value,
        onValueChange = {
            search.value=it
            noteViewModel.searchNotes(search.value)
                        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        shape = RoundedCornerShape(20.dp),
        leadingIcon = {
            Image(painter = painterResource(R.drawable.ic_search)
                , contentDescription = "ic_search", modifier = Modifier.size(20.dp))
        },
        placeholder = {Text("Search Notes")},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedIndicatorColor = primary,
        ),
        singleLine = true
    )
}
@Composable
fun Extension(ex: ImageVector, content: String, nav:()-> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable{nav()}.padding(horizontal = 12.dp)) {
    Icon(imageVector =ex
        ,contentDescription =null
        , Modifier.size(30.dp)
        , tint = text_color.value)
    Text(content, color = text_color.value, fontSize = 18.sp)}
}


@Composable
fun Footpage(navController: NavController,
             navToProfile:()->Unit={navController.navigate(AppDestinations.Profile)},
             navToHelp:()->Unit={navController.navigate(AppDestinations.Help)},
             topvalues: Int=12){
    Row (modifier = Modifier
        .background(primary)
        .padding(topvalues.dp)
        , horizontalArrangement = Arrangement.SpaceBetween
        ){
        Spacer(modifier = Modifier.weight(1f))
        Extension(Icons.Filled.AccountCircle,"Me", nav = navToProfile )
        Spacer(modifier = Modifier.weight(3f))
        Extension(Icons.Filled.Error,"Help", nav = navToHelp)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun FooterWithAddButton(
    navController: NavController,
    diaLogState: MutableState<Boolean>,
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center,

    ) {
        Footpage(navController)
        Box(
            modifier = Modifier
                .offset(y = (-35).dp) // đẩy nút lên
        ) {
            AddNoteButton(diaLogState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleModalBottomSheetExample(
    navController: NavController,
    showBottomSheet: MutableState<Boolean>,

    navToAddNote:()->Unit={navController.navigate(AppDestinations.AddNote)},
    navToAddTodo:()->Unit={navController.navigate(AppDestinations.AddTodo)}) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    // Dùng luồng phụ để chạy showBootomsheet
    val scope = rememberCoroutineScope()

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState,
            containerColor = bg_color.value,
            content = {
                // Nội dung bên trong Bottom Sheet của bạn
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        ,
                    horizontalAlignment = Alignment.CenterHorizontally // Căn giữa nội dung
                ) {

                    // Add note
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet.value = false
                                    navToAddNote()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Keyboard,
                                null,
                                Modifier.size(30.dp),
                                tint = text_color.value
                            )
                            Spacer(modifier = Modifier.padding(12.dp))
                            Text(
                                "Add note",
                                color = text_color.value,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }


                    //Add To do list
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet.value = false
                                    navToAddTodo()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckBox,
                                null,
                                Modifier.size(30.dp),
                                tint = text_color.value
                            )
                            Spacer(modifier = Modifier.padding(12.dp))
                            Text(
                                "Add To-do",
                                color = text_color.value,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )

                        }
                    }
                }
            }
        )
    }

}
