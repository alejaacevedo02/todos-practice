package com.packt.todoassigment

import androidx.lifecycle.ViewModel
import com.packt.todoassigment.model.Todo
import com.packt.todoassigment.model.TodoEvent
import com.packt.todoassigment.model.TodoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.atomic.AtomicInteger

 val previewTodos = listOf(
    Todo(
        id = 1,
        title = "Finalize report",
        description = "Include Q4 sales figures and projections.",
        isChecked = true
    ),
    Todo(id = 2, title = "Schedule team meeting", description = "Discuss next sprint goals."),
    Todo(
        id = 3,
        title = "Grocery shopping",
        description = "Milk, Eggs, Bread, Coffee",
        isChecked = false
    ),
    Todo(id = 4, title = "Book dentist appointment"), // Minimal data
    Todo(
        id = 5,
        title = "Plan weekend trip",
        description = "Research destinations and accommodation.",
        isChecked = false
    ),
    Todo(id = 6, title = "Finish todos app", description = "philipp course", isChecked = true),
    Todo(id = 7, title = "1c Pay utility bills", isChecked = true),
    Todo(id = 8, title = "2 Pay utility bills", isChecked = true),
    Todo(id = 9, title = "3 Pay utility bills", isChecked = true)

)
class TodosViewModel : ViewModel() {

    private val _state = MutableStateFlow(TodoUiState(todos = previewTodos))
    val state = _state.asStateFlow()
    private val idCounter = AtomicInteger(0)

    fun onEvent(todoEvent: TodoEvent) {
        when (todoEvent) {
            TodoEvent.AddTodo -> updateTodos()
            is TodoEvent.CheckTodoClicked -> checkTodo(todoEvent.id, todoEvent.isChecked)
            is TodoEvent.DeleteTodoClicked -> deleteTodo(todoEvent.id)
            is TodoEvent.OnDescriptionChanged -> updateNewDescription(todoEvent.newDescription)
            is TodoEvent.OnTitleChanged -> updateNewTitle(todoEvent.newTitle)
        }
    }

    private fun checkTodo(id: Int, isChecked: Boolean) {
        _state.update { todoUiState ->
            todoUiState.copy(
                todos = state.value.todos.map { todo ->
                    if (todo.id == id) {
                        todo.copy(isChecked = isChecked)
                    } else {
                        todo
                    }
                }
            )
        }
    }

    private fun deleteTodo(id: Int) {
        _state.update { todoUiState ->
            todoUiState.copy(
                todos = state.value.todos.filterNot { it.id == id }
            )

        }
    }

    private fun updateTodos() {
        val newId = idCounter.getAndIncrement()
        val newTodo = Todo(
            id = newId,
            title = state.value.newTitle,
            description = state.value.newDescription,
            isChecked = false
        )
        _state.update {
            it.copy(
                todos = state.value.todos + newTodo,
                newTitle = "",
                newDescription = ""
            )
        }
    }

    private fun updateNewTitle(newTitle: String) {
        _state.update {
            it.copy(
                newTitle = newTitle
            )
        }
    }

    private fun updateNewDescription(newDescription: String) {
        _state.update {
            it.copy(
                newDescription = newDescription
            )
        }
    }


}