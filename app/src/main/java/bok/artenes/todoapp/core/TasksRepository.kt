package bok.artenes.todoapp.core

import bok.artenes.todoapp.common.MediatorObservableValue
import bok.artenes.todoapp.common.Observable
import bok.artenes.todoapp.utils.KeyValueRepository

/**
 * Repository to store tasks.
 */
class TasksRepository(
    private val keyValueRepository: KeyValueRepository,
    private val tasksSerializer: TasksSerializer
) {

    /**
     * Transforms the serialized list of tasks from the [KeyValueRepository]
     * to a list of tasks.
     */
    private val tasksMutator = object : MediatorObservableValue.Mutator<String, List<Task>> {
        override fun mutate(input: String): List<Task> {
            return tasksSerializer.deserialize(input)
        }
    }

    /**
     * Get an observable list of tasks.
     */
    fun getTasks(): Observable<List<Task>> {
        return MediatorObservableValue(
            keyValueRepository.get(TASKS_KEY),
            tasksMutator
        )
    }

    /**
     * Save a task.
     */
    fun save(task: Task) {
        val tasks = readFromKeyValueRepo()
        val index = tasks.indexOfFirst { it.uuid == task.uuid }
        if (index > -1) {
            tasks[index] = task
        } else {
            tasks.add(task)
        }
        writeInKeyValueRepo(tasks)
    }

    /**
     * Delete a task.
     */
    fun delete(task: Task) {
        val tasks = readFromKeyValueRepo()
        tasks.remove(task)
        writeInKeyValueRepo(tasks)
    }

    private fun readFromKeyValueRepo(): MutableList<Task> {
        val serialized = keyValueRepository.getValue(TASKS_KEY)
        return tasksSerializer.deserialize(serialized).toMutableList()
    }

    private fun writeInKeyValueRepo(tasks: MutableList<Task>) {
        val serialized = tasksSerializer.serialize(tasks)
        keyValueRepository.put(TASKS_KEY, serialized)
    }

    companion object {
        private const val TASKS_KEY = "TASKS"
    }

}