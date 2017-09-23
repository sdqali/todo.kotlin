package com.sdqali.todo

import com.sdqali.todo.config.AppConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    System.getenv("ROOT_URL")?.let { AppConfig.rootUrl = it }
    SpringApplication.run(Application::class.java, *args)
}