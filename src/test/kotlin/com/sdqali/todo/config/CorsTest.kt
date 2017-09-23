package com.sdqali.todo.config

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpMethod.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(value = SpringRunner::class)
class CorsTest {
    @Autowired
    private lateinit var wac: WebApplicationContext

    private lateinit var mvc: MockMvc

    @Before
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
            .build()
    }

    @Test
    fun ensureCorsHeadersAreSet() {
        arrayListOf(GET, POST, PUT, PATCH, DELETE, OPTIONS).forEach {
            mvc.perform(options("/")
                .header("Access-Control-Request-Method", it.name)
                .header("Origin", "http://example.com"))
                .andExpect(status().isOk)
        }
    }
}
