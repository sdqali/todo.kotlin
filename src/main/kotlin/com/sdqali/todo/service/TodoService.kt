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

    fun patch(id: UUID, input: Map<String, String>): TodoItem? {
        items.find { it.id == id}?.let { item ->
            input["title"]?.let { item.title = it}
            input["completed"]?.let { item.completed = it.toBoolean()}
            return item
        }
        return null
    }

}

data class TodoItem(
    val id: UUID = UUID.randomUUID(),
    var title: String,
    var completed: Boolean = false,
    val url: String = "${AppConfig.rootUrl}/$id"
)
