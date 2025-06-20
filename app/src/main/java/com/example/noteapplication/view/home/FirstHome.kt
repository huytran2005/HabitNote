package com.example.noteapplication.view.home
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells.Fixed
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapplication.AppDestinations
import com.example.noteapplication.Model.Note
import com.example.noteapplication.Model.Todo
import com.example.noteapplication.R
import com.example.noteapplication.ViewModel.NoteState
import com.example.noteapplication.ViewModel.NoteViewModel
import com.example.noteapplication.ui.theme.bg_color
import com.example.noteapplication.ui.theme.noteColors
import com.example.noteapplication.ui.theme.secondary
import com.example.noteapplication.ui.theme.text_color
import com.example.noteapplication.view.loginAccount.login.LoadingScreen

@Composable
fun TitleFirstHome(search: MutableState<String>){
    Row (verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)){
        Text("Notes", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = text_color.value)
        SearchBox(search)
    }
}
@Composable
fun ContentEmptyHome(){
    Image(painterResource(R.drawable.bg_firsthome)
        , contentDescription = null, Modifier.size(210.dp).padding(10.dp))
    Text(
        "Create your first note",
        fontSize = 20.sp,
        fontWeight = FontWeight.Light,
        color = text_color.value
    )
}
@Composable
fun FrameNote(note: Note,
              onClick:()->Unit={},
              color: Color){
    Card (colors = CardDefaults.cardColors(color),
        modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp)
        .clickable{onClick()},
        shape = RoundedCornerShape(12.dp),
    ){
        Column (Modifier.padding(24.dp)){
            Text(note.title, fontSize = 24.sp, fontWeight = FontWeight.Bold,color=Color.Black)
            Text(note.content, fontSize = 18.sp,color=Color.Black)
        }
    }
}
@Composable
fun FrameTodo(
    note: Note,
    onClick: () -> Unit = {},
    viewModel: NoteViewModel = viewModel(),
    color: Color
) {
    val todoList = remember(note.id) {
        mutableStateListOf<Todo>().apply { addAll(note.todoList) }
    }

    Card(
        colors = CardDefaults.cardColors(color),
        modifier = Modifier
            .clickable { onClick() }
            .padding(top = 12.dp)
    ) {
        Column(Modifier.padding(24.dp)) {
            Text(note.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            todoList.forEachIndexed { index, todo ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = todo.boolean,
                        onCheckedChange = { checked ->
                            todoList[index] = todo.copy(boolean = checked)

                            // Gọi viewModel để lưu bản Note mới
                            val updatedNote = note.copy(todoList = todoList.toList())

                            // Lưu lên Firestore
                            viewModel.saveNote(updatedNote)
                        }
                    )
                    Text(todo.content, fontSize = 18.sp,color=Color.Black)
                }
            }
        }
    }
}

@Composable
fun ContentHome(viewModel: NoteViewModel = viewModel(),
                navController: NavController){
    val notes by viewModel.noteList.collectAsState()
    val state = viewModel.saveStatus.collectAsState().value
    val context = LocalContext.current
    when(state){
        is NoteState.Loading -> {
            LoadingScreen()
        }
        is NoteState.Success ->{
            if(notes.isEmpty()){
                Column(Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    ContentEmptyHome()

                }
            }
            else {
                LazyVerticalStaggeredGrid(
                    columns = Fixed(2),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        itemsIndexed (notes) {index, item ->
                            val color = noteColors[index % noteColors.size]
                            if (item.todoList.isEmpty()) {
                                FrameNote(note = item, onClick = {
                                    viewModel.selectNote(item)
                                    navController.navigate(AppDestinations.EditNote)
                                },
                                    color=color)

                            } else {
                                FrameTodo(
                                    note = item,
                                    onClick = {
                                        viewModel.selectNote(item)
                                        navController.navigate(AppDestinations.EditTodo)
                                    },
                                    color=color
                                )

                            }
                        }
                    })
            }
        }
        is NoteState.Error ->{
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
        is NoteState.Idle -> {}
        }
    }

@Composable
fun SearchContent(filler: List<Note>
                  ,viewModel: NoteViewModel,
                  navController: NavController){
    LazyVerticalStaggeredGrid(
        columns = Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            itemsIndexed (filler){index,item->
                val color = noteColors[index % noteColors.size]
                if(item.todoList== listOf<Todo>()) {
                FrameNote(note = item, onClick = {
                    viewModel.selectNote(item)
                    navController.navigate(AppDestinations.EditNote)
                },
                    color = color)
        }
        else{
            FrameTodo(note=item, onClick = {
                viewModel.selectNote(item)
                navController.navigate(AppDestinations.EditTodo)},
                color=color)
        }
        } }
    )
}
@Composable
fun FirstHomeScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: NoteViewModel = viewModel()
)  {
    var search = remember { mutableStateOf("") }
    val diaLogState = remember { mutableStateOf(false) }
    val fillter = viewModel.filteredNotes.collectAsState()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(bg_color.value),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleFirstHome(search)
            viewModel.getNotes()
            if (fillter.value.isEmpty() and search.value.isEmpty()){
                ContentHome(navController=navController)
            }
            else
                SearchContent(fillter.value,viewModel,navController)


        }
            FooterWithAddButton(navController, diaLogState)
            SimpleModalBottomSheetExample(navController, diaLogState)
    }

}

