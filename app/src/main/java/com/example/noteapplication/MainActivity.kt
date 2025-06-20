package com.example.noteapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapplication.ViewModel.NoteViewModel
import com.example.noteapplication.ui.theme.NoteApplicationTheme
import com.example.noteapplication.view.home.Editor.AddNoteScreen
import com.example.noteapplication.view.home.Editor.AddTodoScreen
import com.example.noteapplication.view.home.Editor.EditNoteScreen
import com.example.noteapplication.view.home.Editor.EditTodoScreen
import com.example.noteapplication.view.home.FirstHomeScreen
import com.example.noteapplication.view.home.others.AboutScreen
import com.example.noteapplication.view.home.others.HelpScreen
import com.example.noteapplication.view.home.others.ProfileScreen
import com.example.noteapplication.view.loginAccount.login.LoginScreen
import com.example.noteapplication.view.loginAccount.register.RegisterScreen
import com.example.noteapplication.view.onboarding.OnBoardingScreen1
import com.example.noteapplication.view.onboarding.SplashScreen
import com.example.noteapplication.view.onboarding.SwitchUI
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteApplicationTheme {
                FirebaseApp.initializeApp(this)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp(
                        paddingValues = innerPadding
                    )
                }
            }
        }
    }
}

object AppDestinations {
    const val Splash = "splash"
    const val OnBoarding = "onboarding"
    const val OnBoarding1 = "onboarding1"
    const val Login = "login"
    const val Register = "register"
    const val AddNote = "addnote"
    const val AddTodo = "addtodo"
    const val FirstHome= "firstHome"
    const val Profile = "profile"
    const val Help="help"
    const val About ="about"
    const val EditNote="editnote"
    const val EditTodo = "edittodo"
}
@Composable
fun MainApp(paddingValues: PaddingValues){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppDestinations.Splash){
        composable(AppDestinations.Splash){SplashScreen(paddingValues = paddingValues,navController=navController)}
        composable (AppDestinations.OnBoarding){ SwitchUI(paddingValues = paddingValues,navController=navController) }
        composable ( AppDestinations.OnBoarding1 ){ OnBoardingScreen1(paddingValues = paddingValues,navController=navController) }
        composable (AppDestinations.Login){ LoginScreen(paddingValues = paddingValues, navController = navController) }
        composable (AppDestinations.Register){ RegisterScreen(paddingValues= paddingValues,navController=navController) }
        composable (AppDestinations.FirstHome){ FirstHomeScreen(paddingValues,navController) }
        composable (AppDestinations.Profile){ ProfileScreen(paddingValues,navController) }
        composable (AppDestinations.Help){ HelpScreen(paddingValues,navController) }
        composable (AppDestinations.AddNote){ AddNoteScreen(paddingValues,navController) }
        composable (AppDestinations.AddTodo){ AddTodoScreen(paddingValues,navController) }
        composable (AppDestinations.About){ AboutScreen(paddingValues,navController) }
        composable (AppDestinations.EditNote){ EditNoteScreen(paddingValues,navController) }
        composable (AppDestinations.EditTodo){ EditTodoScreen(paddingValues,navController) }
    }
    }

