package bok.artenes.todoapp.common

import android.app.Application
import bok.artenes.todoapp.core.TasksRepository
import bok.artenes.todoapp.core.TasksSerializer
import bok.artenes.todoapp.utils.KeyValueRepository

/**
 * Simple solution to provide some common dependencies to the app.
 * In a proper app we would use Dagger 2.
 */
object Dependencies {

    /**
     * This needs to be assigned as soon as possible before calling the other dependencies
     */
    lateinit var application: Application

    /**
     * Our repository that will manage tasks.
     */
    val repository by lazy {
        TasksRepository(
            KeyValueRepository(application),
            TasksSerializer()
        )
    }

}