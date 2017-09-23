package com.sdqali.todo.service

import com.sdqali.todo.config.AppConfig
import org.springframework.stereotype.Service
import java.util.*

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

    fun get(id: String): TodoItem? {
        return items.find { it.id.toString() == id}
    }

}

data class TodoItem(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val completed: Boolean = false,
    val url: String = "${AppConfig.rootUrl}/$id"
)
