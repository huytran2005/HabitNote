package com.example.noteapplication.Model


data class Note(
    val title: String="",
    val content: String="",
    val id :String= "",
    val todoList: List<Todo> = emptyList()
)

data class Todo(
    val content: String = "",
    var boolean: Boolean = false
)