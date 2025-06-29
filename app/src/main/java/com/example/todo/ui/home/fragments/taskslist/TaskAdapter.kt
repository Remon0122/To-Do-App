package com.example.todo.ui.home.fragments.taskslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.models.entity.Task
import com.example.todo.ui.home.fragments.addtask.AddTaskFragment
import com.example.todo.ui.util.getFormattedTime
import com.route.todo.R
import com.route.todo.databinding.ItemTaskBinding
import java.util.*

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    private var tasksList = mutableListOf<Task>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTasksList(tasks: MutableList<Task>) {
        tasksList.clear()
        tasksList.addAll(tasks)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(private var binding  : ItemTaskBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (task: Task) {
            binding.title.text = task.title
            val calendar = Calendar.getInstance().apply {
                timeInMillis = task.time
            }

            val hr = calendar.get(Calendar.HOUR)
            val minutes = calendar.get(Calendar.MINUTE)
            binding.time.text = getFormattedTime(hr, minutes)

            if (task.isDone) {
                binding.btnTaskIsDone.setImageResource(R.drawable.check_mark)
                binding.title.alpha = 0.4f
            } else {
                binding.btnTaskIsDone.setImageResource(R.drawable.check_mark)
                binding.title.alpha = 1f
            }

            binding.liftView.setOnClickListener {
                onDeleteBtnClickListener?.OnClick(adapterPosition, task)
            }

            binding.btnTaskIsDone.setOnClickListener {
                if (!task.isDone) {
                    onDoneBtnClickListener?.OnClick(adapterPosition, task)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = tasksList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasksList[position])
    }
    var onDeleteBtnClickListener : OnTaskClickListener ?= null
    var onDoneBtnClickListener : OnTaskClickListener ?= null

    fun interface OnTaskClickListener{
        fun OnClick(position: Int,task: Task)
    }
}
