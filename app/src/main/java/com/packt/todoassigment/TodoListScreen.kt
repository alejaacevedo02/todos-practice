package com.packt.todoassigment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.packt.todoassigment.model.Todo
import com.packt.todoassigment.model.TodoEvent
import com.packt.todoassigment.ui.theme.TodoAssigmentTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.todoassigment.model.TodoUiState


@Composable
fun TodoItemRoot(modifier: Modifier = Modifier) {
    val viewModel = viewModel<TodosViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    TodoItemScreen(
        modifier = modifier,
        state,
        viewModel::onEvent
    )

}

@Composable
fun TodoItemScreen(
    modifier: Modifier = Modifier,
    todoUiState: TodoUiState,
    onEvent: (TodoEvent) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Assign a weight to the lazy column to only take the space after non weighted element is drawn
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(todoUiState.todos) { todo ->
                TodoItem(
                    todo,
                    onEvent = onEvent
                )
            }
        }
        AddTodo(
            title = todoUiState.newTitle,
            description = todoUiState.newDescription,
            onEvent = onEvent,
        )
    }
}

@Composable
fun AddTodo(
    title: String,
    description: String,
    onEvent: (TodoEvent) -> Unit
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = title,
                onValueChange = { newText ->
                    onEvent(TodoEvent.OnTitleChanged(newText))
                },
                placeholder = { Text("Title") })

            TextField(
                value = description,
                onValueChange = { newText ->
                    onEvent(TodoEvent.OnDescriptionChanged(newText))

                },
                placeholder = { Text("Description") })
        }
        Button(onClick = { onEvent(TodoEvent.AddTodo) }) {
            Text("Add")
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                todo.title,
                fontWeight = FontWeight.Bold,
                textDecoration = if (todo.isChecked) TextDecoration.LineThrough
                else TextDecoration.None
            )
            Text(
                todo.description,
                textDecoration = if (todo.isChecked) TextDecoration.LineThrough
                else TextDecoration.None
            )
        }
        Checkbox(
            checked = todo.isChecked,
            onCheckedChange = { newChange ->
                onEvent(TodoEvent.CheckTodoClicked(todo.id, newChange))
            }
        )
        IconButton(onClick = {
            onEvent(TodoEvent.DeleteTodoClicked(todo.id))
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
internal fun TodoItemPreview() {
    val previewTodos: List<Todo> = listOf(
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
        Todo(id = 7, title = "2 Pay utility bills", isChecked = true),
        Todo(id = 7, title = "3 Pay utility bills", isChecked = true)

    )
    TodoAssigmentTheme {
        TodoItemScreen(
            todoUiState = TodoUiState(
                previewTodos,
                newTitle = "",
                newDescription = ""
            ),
            onEvent = {}
        )
    }
}