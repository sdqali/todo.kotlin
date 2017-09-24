package com.sdqali.todo.service

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class TodoServiceTest {
    val todoService: TodoService = TodoService()

    @Before
    fun setup() {
        todoService.clear()
    }

    @Test
    fun canAddItemFromMap() {
        val item = todoService.addFrom(mapOf("title" to "do something"))
        assertEquals(1, todoService.list().size)
        assertFalse(item!!.completed)
        assertNotNull(item!!.url)
    }

    @Test
    fun canRetrieveItemForId() {
        val item = todoService.addFrom(mapOf("title" to "do something"))
        assertEquals(item, todoService.get(item!!.id))
    }

    @Test
    fun canPatchItem() {
        val item = todoService.addFrom(mapOf("title" to "do something"))
        todoService.patch(item!!.id, mapOf("title" to "something else", "completed" to "true"))
        assertEquals("something else", item.title)
        assertTrue(item.completed)
    }

    @Test
    fun canDelete() {
        val item = todoService.addFrom(mapOf("title" to "do something"))
        todoService.delete(item!!.id)
        assertNull(todoService.get(item!!.id))
    }
}