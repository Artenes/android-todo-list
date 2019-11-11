package bok.artenes.todoapp.tasks

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bok.artenes.todoapp.R
import bok.artenes.todoapp.common.Dependencies
import bok.artenes.todoapp.common.Observer
import bok.artenes.todoapp.core.Task
import bok.artenes.todoapp.core.TasksRepository
import bok.artenes.todoapp.utils.Device
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity() {

    private val repository: TasksRepository by lazy {
        Dependencies.repository
    }

    private val adapter: TasksAdapter by lazy {
        TasksAdapter()
    }

    private val layoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private val dividerDecorator by lazy {
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    }

    private val tasksObserver = object : Observer<List<Task>> {
        override fun accept(value: List<Task>) {
            adapter.submitList(value)
            textViewNoTasks.visibility = if (value.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private val tasks by lazy {
        repository.getTasks()
    }

    private val onTaskInsert = TextView.OnEditorActionListener { _, actionId, _ ->
        when (actionId) {
            EditorInfo.IME_ACTION_SEND -> {
                val taskDescription = editTextTask.text.toString()
                val task = Task(description = taskDescription, done = false)
                repository.save(task)
                editTextTask.setText("")
                Device.hideKeyboardOn(editTextTask)
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dependencies.application = application

        setContentView(R.layout.activity_tasks)
        recyclerViewTasks.layoutManager = layoutManager
        recyclerViewTasks.adapter = adapter
        recyclerViewTasks.addItemDecoration(dividerDecorator)
        (recyclerViewTasks.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        editTextTask.setOnEditorActionListener(onTaskInsert)

        tasks.observe(tasksObserver)
    }

    override fun onDestroy() {
        super.onDestroy()

        tasks.ignore(tasksObserver)
    }

}