package com.packt.todoassigment.model

sealed interface TodoEvent {
    data class CheckTodoClicked(val id: Int, val isChecked: Boolean) : TodoEvent
    data class DeleteTodoClicked(val id: Int) : TodoEvent
    data class OnTitleChanged(val newTitle: String) : TodoEvent
    data class OnDescriptionChanged(val newDescription: String) : TodoEvent
    data object AddTodo : TodoEvent
}