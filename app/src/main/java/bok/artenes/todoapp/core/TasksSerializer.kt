package bok.artenes.todoapp.core

/**
 * Serializer for [Task]s
 */
class TasksSerializer {

    /**
     * Serializes a list of [Task] in the format
     * taskA;false|taskB;true|taskC;false
     */
    fun serialize(tasks: List<Task>): String {
        var serialized = ""
        tasks.forEach {
            val escapedDescription = escapeDescription(it.description)
            serialized += "${it.uuid};${escapedDescription};${it.done}|"
        }
        return serialized.removeSuffix("|")
    }

    /**
     * Deserialize a list of [Task].
     * Empty strings returns an empty list.
     */
    fun deserialize(serializedTasks: String): List<Task> {
        val tasks = mutableListOf<Task>()
        if (serializedTasks.isEmpty()) {
            return tasks
        }
        val parts = serializedTasks.split(Regex("(?<!/)\\|"))
        parts.forEach {
            val attributes = it.split(Regex("(?<!/);"))
            val uuid = attributes[0]
            val unescapedDescription = unescapeDescription(attributes[1])
            val done = attributes[2] == "true"
            val task = Task(uuid = uuid, description = unescapedDescription, done = done)
            tasks.add(task)
        }
        return tasks
    }

    private fun escapeDescription(description: String): String {
        return description.replace(";", "/;").replace("|", "/|")
    }

    private fun unescapeDescription(description: String): String {
        return description.replace("/;", ";").replace("/|", "|")
    }

}