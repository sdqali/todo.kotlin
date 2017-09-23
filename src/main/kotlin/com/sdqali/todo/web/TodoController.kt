package com.sdqali.todo.web

import com.sdqali.todo.service.TodoItem
import com.sdqali.todo.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController() {
    @Autowired
    lateinit var todoService: TodoService

    @GetMapping("/")
    fun list(): List<TodoItem> {
        return todoService.list()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<TodoItem> {
        todoService.get(id)?.let {
            return ResponseEntity(it, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
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
