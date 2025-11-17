package ru.lnkr.todo.repository

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import java.util.Calendar
import java.util.Date

open class TodoViewModel : ViewModel() {
    private var _items = MutableStateFlow<List<TodoItem>>(mutableListOf())
    val items: StateFlow<List<TodoItem>>
        get() = _items.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        saveItem(
            TodoItem(
                id = "1",
                text = "Купить продукты",
                importance = Importance.HIGH,
                createdAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            )
        )

        saveItem(
            TodoItem(
                id = "2",
                text = "Закончить отчет",
                importance = Importance.HIGH,
                isCompleted = true,
                createdAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            )
        )

        saveItem(
            TodoItem(
                id = "3",
                text = "Позвонить маме",
                importance = Importance.NONE,
                createdAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            )
        )

        saveItem(
            TodoItem(
                id = "4",
                text = "Сходить в спортзал",
                importance = Importance.LOW,
                createdAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            )
        )
    }

    fun saveItem(item: TodoItem) {
        _items.value = _items.value.map {
            if (it.id == item.id) {
                item
            } else {
                it
            }
        } + if (_items.value.none { it.id == item.id }) listOf(item) else emptyList()
    }

    fun deleteItem(itemId: String) {
        _items.value = _items.value.filterNot { it.id == itemId }
    }

    fun completeItem(itemId: String) {
        _items.value = _items.value.map {
            if (it.id == itemId) {
                it.copy(
                    isCompleted = !it.isCompleted,
                    modifiedAt = Date()
                )
            } else {
                it
            }
        }
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}