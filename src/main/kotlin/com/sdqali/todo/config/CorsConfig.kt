package com.sdqali.todo.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.*
import org.springframework.web.servlet.config.annotation.CorsRegistration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class CorsConfig : WebMvcConfigurerAdapter() {
    val allowedMethods = arrayListOf(GET, POST, PUT, PATCH, DELETE, OPTIONS)

    override fun addCorsMappings(registry: CorsRegistry?) {
        super.addCorsMappings(registry)
        registry?.let {
            val registration: CorsRegistration = it.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("content-type")
            registration.allowedMethods(*allowedMethods
                .map(HttpMethod::name)
                .toTypedArray())
        }
    }
}