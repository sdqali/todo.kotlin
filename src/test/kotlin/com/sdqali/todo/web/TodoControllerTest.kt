package com.sdqali.todo.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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
            .andExpect(status().isOk)
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
            .andExpect(content().bytes(objectMapper.writeValueAsBytes(emptyArray<String>())))

    }
}