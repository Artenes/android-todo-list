package bok.artenes.todoapp.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class TasksSerializerTest {

    private val serializer = TasksSerializer()

    @Test
    fun serialize_emptyList() {
        val emptyList = emptyList<Task>()

        val serialized = serializer.serialize(emptyList)

        assertEquals("", serialized)
    }

    @Test
    fun serialize_escapeDelimiters() {
        val task = Task(uuid = "uuid", description = "Do something; with delimiters|", done = false)

        val serialized = serializer.serialize(listOf(task))

        assertEquals("uuid;Do something/; with delimiters/|;false", serialized)
    }

    @Test
    fun deserialize_dealsWithEscapedDelimiters() {
        val serialized = "uuid;Do something/; with delimiters/|;false"

        val tasks = serializer.deserialize(serialized)

        assertEquals("Do something; with delimiters|", tasks[0].description)
        assertFalse(tasks[0].done)
    }

    @Test
    fun deserialize_ignoresEmptyStrings() {
        val serialized = ""

        val tasks = serializer.deserialize(serialized)

        assertEquals(0, tasks.size)
    }

}