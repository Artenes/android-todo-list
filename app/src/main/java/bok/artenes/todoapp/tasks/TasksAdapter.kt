package bok.artenes.todoapp.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bok.artenes.todoapp.R
import bok.artenes.todoapp.common.Dependencies
import bok.artenes.todoapp.core.Task
import kotlinx.android.synthetic.main.item_tasks.view.*

/**
 * An adapter to display a list of tasks.
 */
class TasksAdapter : ListAdapter<Task, TasksAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    private val repository = Dependencies.repository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tasks, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)

        toggleStroke(holder.checkBox, task.done)
        holder.checkBox.text = task.description
        holder.checkBox.isChecked = task.done
        holder.itemView.setOnClickListener { onTaskCheckedChanged(task) }
        holder.button.setOnClickListener { onDeleteTask(task) }
    }

    private fun onTaskCheckedChanged(task: Task) {
        repository.save(task.copy(done = !task.done))
    }

    private fun onDeleteTask(task: Task) {
        repository.delete(task)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.checkBoxTask
        val button: Button = itemView.buttonDelete
    }

    //TODO-TIL Paint flags are used to strike a text programmatically.
    private fun toggleStroke(textView: TextView, stroke: Boolean) {
        if (stroke) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            //TODO-TIL To invert bits we use a function called inv() in the variable.
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }

    }

}

