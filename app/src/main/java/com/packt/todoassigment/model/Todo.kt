package com.packt.todoassigment.model

data class Todo(
    val id: Int,
    val title: String = "",
    val description : String = "",
    val isChecked : Boolean = false
)



