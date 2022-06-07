package br.com.castroandrius.taskmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class TaskManagerApplication

fun main(args: Array<String>) {
	runApplication<TaskManagerApplication>(*args)
}