package com.packt.todoassigment.model

data class TodoUiState(
    val todos: List<Todo> = listOf(),
    val newTitle: String = "",
    val newDescription: String = ""
)
