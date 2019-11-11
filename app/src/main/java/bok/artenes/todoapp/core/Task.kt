package bok.artenes.todoapp.core

import java.util.*

/**
 * A task.
 */
data class Task(
    val uuid: String = UUID.randomUUID().toString(),
    val description: String,
    val done: Boolean
)