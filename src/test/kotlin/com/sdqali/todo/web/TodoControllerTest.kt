package com.sdqali.todo.web

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.sdqali.todo.service.TodoItem
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(value = SpringRunner::class)
class TodoControllerTest {
    @Autowired
    private lateinit var wac: WebApplicationContext

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var mvc: MockMvc

    @Before
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
            .build()
        mvc.perform(delete("/")).andExpect(status().isOk)
    }

    @Test
    fun canListItems() {
        mvc.perform(get("/"))
            .andExpect(status().isOk)
    }

    @Test
    fun acceptsAnItemAndReturnsIt() {
        mvc.perform(post("/")
            .content(objectMapper.writeValueAsBytes(mapOf("title" to "do something")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted)
    }

    @Test
    fun cantCreateItemWithoutTitle() {
        mvc.perform(post("/")
            .content(objectMapper.writeValueAsBytes(mapOf("invalid-param" to "do something")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotAcceptable)
    }

    @Test
    fun canClearItems() {
        mvc.perform(delete("/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun verifyListIsCleared() {
        mvc.perform(delete("/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)

        mvc.perform(get("/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string(objectMapper.writeValueAsString(emptyArray<TodoItem>())))

    }

    @Test
    fun addsAnItemToList() {
        mvc.perform(post("/")
            .content(objectMapper.writeValueAsBytes(mapOf("title" to "do something")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted)

        val mvcResult = mvc.perform(get("/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andReturn()
        val items : List<Map<String, String>> = objectMapper.readValue(mvcResult.response.contentAsByteArray, object : TypeReference<List<Map<String, String>>>() {})
        assertEquals(1, items.size)
        assertEquals("do something", items[0]["title"])
        assertNotNull(items[0]["url"])
    }

    @Test
    fun itemsHaveUrls() {
        val mvcResult = mvc.perform(post("/")
            .content(objectMapper.writeValueAsBytes(mapOf("title" to "do something")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted)
            .andReturn()
        val item : Map<String, String> = objectMapper.readValue(mvcResult.response.contentAsByteArray, object : TypeReference<Map<String, String>>() {})

        mvc.perform(get(item["url"]))
            .andExpect(status().isOk)

    }

    @Test
    fun returns404ForNonExistentItem() {
        mvc.perform(get("/${UUID.randomUUID()}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    @Test
    fun canPatchTitle() {
        var mvcResult = createItem()
        var item : Map<String, String> = objectMapper.readValue(mvcResult.response.contentAsByteArray, object : TypeReference<Map<String, String>>() {})

        mvcResult = mvc.perform(patch(item["url"]).content(objectMapper.writeValueAsBytes(mapOf("title" to "do something else")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andReturn()
        item = objectMapper.readValue(mvcResult.response.contentAsByteArray, object : TypeReference<Map<String, String>>() {})
        assertEquals("do something else", item["title"])
    }

    @Test
    fun canPatchStatus() {
        var mvcResult = createItem()
        var item : Map<String, String> = objectMapper.readValue(mvcResult.response.contentAsByteArray, object : TypeReference<Map<String, String>>() {})

        mvcResult = mvc.perform(patch(item["url"]).content(objectMapper.writeValueAsBytes(mapOf("completed" to "true")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andReturn()
        item = objectMapper.readValue(mvcResult.response.contentAsByteArray, object : TypeReference<Map<String, String>>() {})
        assertEquals("true", item["completed"])
    }

    @Test
    fun canDelete() {
        var mvcResult = createItem()
        var item : Map<String, String> = objectMapper.readValue(mvcResult.response.contentAsByteArray, object : TypeReference<Map<String, String>>() {})

        mvc.perform(delete(item["url"])
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
        mvc.perform(get(item["url"])
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    private fun createItem(): MvcResult {
        return mvc.perform(post("/")
            .content(objectMapper.writeValueAsBytes(mapOf("title" to "do something")))
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }
}