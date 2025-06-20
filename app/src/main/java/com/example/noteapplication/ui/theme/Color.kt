package com.example.noteapplication.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

var bg_color = mutableStateOf (Color("#F1F1F1".toColorInt()))
val primary = Color("#FFB347".toColorInt())

var secondary = Color("#B69CFF".toColorInt())
var text_color= mutableStateOf(Color.Black)
val noteColors = listOf<Color>(
    Color("#FF9E9E".toColorInt()),
    Color("#DD6A57".toColorInt()),
    Color("#FFF599".toColorInt()),
    Color("#91F48F".toColorInt()),
    Color("#9EFFFF".toColorInt()),
    Color("#FD99FF".toColorInt()),
    Color("#B69CFF".toColorInt()),
    Color("#624AF2".toColorInt()),
    Color("#FCDDEC".toColorInt()),



)