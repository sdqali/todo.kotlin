package com.sdqali.todo.service

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class TodoServiceTest {
    val todoService: TodoService = TodoService()

    @Test
    fun canAddItemFromMap() {
        val item = todoService.addFrom(mapOf("title" to "do something"))
        assertEquals(1, todoService.list().size)
        assertFalse(item!!.completed)
    }
}