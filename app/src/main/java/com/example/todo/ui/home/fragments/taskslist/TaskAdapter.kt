package com.example.todo.ui.home.fragments.taskslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.models.entity.Task
import com.example.todo.ui.util.getFormattedTime
import com.route.todo.databinding.ItemTaskBinding
import java.util.Calendar

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    private var tasksList = mutableListOf<Task>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTasksList(tasks:MutableList<Task>){
        tasksList = tasks
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding: ItemTaskBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(task:Task){
            binding.title.text = task.title
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = task.time
            val hr = calendar.get(Calendar.HOUR)
            val minutes = calendar.get(Calendar.MINUTE)
            binding.time.text = getFormattedTime(hr, minutes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun getItemCount(): Int = tasksList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksList[position]
        holder.bind(task)
    }

}