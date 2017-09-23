package com.sdqali.todo.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController {
    @GetMapping("/")
    fun list(): String {
        return ""
    }
}
