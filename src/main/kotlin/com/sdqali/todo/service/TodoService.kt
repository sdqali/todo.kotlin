package com.sdqali.todo.service

import org.springframework.stereotype.Service

@Service
class TodoService {
    private val items = arrayListOf<TodoItem>()

    fun addFrom(input: Map<String, String>): TodoItem? {
        return input["title"]?.let {
            val item = TodoItem(title = it)
            items.add(item)
            return item
        }
    }

    fun list(): List<TodoItem> {
        return items
    }

    fun clear() {
        items.clear()
    }

}

data class TodoItem(
    val title: String,
    val completed: Boolean = false
)
