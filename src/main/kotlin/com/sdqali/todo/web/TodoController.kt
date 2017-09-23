package com.sdqali.todo.web

import org.springframework.web.bind.annotation.*

@RestController
class TodoController {
    @GetMapping("/")
    fun list(): List<String> {
        return emptyList()
    }

    @PostMapping("/")
    fun create(@RequestBody input: Map<String, String>): Map<String, String> {
        return input
    }

    @DeleteMapping("/")
    fun clear(): String {
        return ""
    }
}
