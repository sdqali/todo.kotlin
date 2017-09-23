package com.sdqali.todo.web

import com.sdqali.todo.service.TodoItem
import com.sdqali.todo.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class TodoController() {
    @Autowired
    lateinit var todoService: TodoService

    @GetMapping("/")
    fun list(): List<TodoItem> {
        return todoService.list()
    }

    @PostMapping("/")
    fun create(@RequestBody input: Map<String, String>): TodoItem? {
        return todoService.addFrom(input)?.let {
            return it
        }
    }

    @DeleteMapping("/")
    fun clear() {
        return todoService.clear()
    }
}
