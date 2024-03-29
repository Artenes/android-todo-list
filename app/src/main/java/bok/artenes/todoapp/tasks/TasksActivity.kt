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

    //we are not using any presentation pattern here such as MVVM or MVP
    //so the activity will hold some logic to deal with the data that comes
    //from the repository.
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

    private val onTaskInsert = TextView.OnEditorActionListener { view, actionId, _ ->
        when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                val taskDescription = view.text.toString()
                val task = Task(description = taskDescription, done = false)
                repository.save(task)
                view.text = ""
                Device.hideKeyboardOn(view)
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
        //TODO-TIL By default, recycler view will animate when items changes. This never seems to
        // be interesting because this makes the items blink in a list update, so to avoid this
        // we have to disable these animations (but the insert and delete ones will remain)
        (recyclerViewTasks.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        editTextTask.setOnEditorActionListener(onTaskInsert)

        tasks.observe(tasksObserver)
    }

    override fun onDestroy() {
        super.onDestroy()

        tasks.ignore(tasksObserver)
    }

}