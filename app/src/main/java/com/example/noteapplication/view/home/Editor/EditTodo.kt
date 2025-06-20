package com.example.noteapplication.view.home.Editor

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.Model.Note
import com.example.noteapplication.Model.Todo
import com.example.noteapplication.ViewModel.NoteViewModel
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.text_color


@Composable
fun TitleEditTodo(title: MutableState<String>) {
    OutlinedTextField(
        value = title.value,
        onValueChange = { title.value = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                "Title",
                color = text_color.value,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = text_color.value
        )
    )
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun EditTodoScreen(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavController,
    viewModel: NoteViewModel= viewModel ()
) {

    val parentEntry = remember(navController) {
        navController.getBackStackEntry(AppDestinations.FirstHome)
    }
    val viewmodel: NoteViewModel = viewModel(parentEntry)
    val note = viewmodel.selectedNote.collectAsState()
    val title = remember { mutableStateOf(note.value?.title ?: "")  }
    val todoList = remember { mutableStateListOf(*note.value?.todoList?.toTypedArray() ?: emptyArray()) }
    val context= LocalContext.current
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(bg_color.value)
    ) {
        TitleEditNote(title = "Add Todo"
            , navController = navController
            , selectedNote = note.value)
        TitleEditTodo(title)
        DynamicToDoEdit(todoList)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.BottomEnd
    ) {
        ComfirmChangeNote(
            onClick = {
                viewModel.saveNote(
                    Note(
                        id = note.value?.id ?: "",
                        title = title.value,
                        todoList = todoList.toList()
                    )
                )
                Toast.makeText(context,"Change Success",Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun DynamicToDoEdit(todoList: SnapshotStateList<Todo>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        todoList.forEachIndexed { index, todo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Nút thêm
                if (index == todoList.lastIndex) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Thêm công việc",
                        tint = text_color.value,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                todoList.add(Todo())
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Transparent,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = todo.content,
                    onValueChange = { newText ->
                        todoList[index] = todo.copy(content = newText)
                    },
                    placeholder = {
                        Text("Nhập công việc...", color = text_color.value)
                    },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    textStyle = TextStyle(fontSize = 20.sp, color = text_color.value)
                )
            }
        }
    }
}
