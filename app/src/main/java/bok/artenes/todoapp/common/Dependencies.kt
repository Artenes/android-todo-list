package bok.artenes.todoapp.common

import android.app.Application
import bok.artenes.todoapp.core.TasksRepository
import bok.artenes.todoapp.core.TasksSerializer
import bok.artenes.todoapp.utils.KeyValueRepository

object Dependencies {

    lateinit var application: Application

    val keyValueRepository by lazy {
        KeyValueRepository(application)
    }

    val tasksSerializer by lazy {
        TasksSerializer()
    }

    val repository by lazy {
        TasksRepository(
            keyValueRepository,
            tasksSerializer
        )
    }

}