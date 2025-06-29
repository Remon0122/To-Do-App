package com.example.todo.ui.home.fragments.HomeViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.database.models.dao.TaskDao
import com.example.todo.database.models.entity.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val dao : TaskDao
) : ViewModel(){
    private val _tasksList = MutableLiveData<List<Task>>()
    val tasksList : LiveData<List<Task>> = _tasksList

    private var currentDate : Long = 0L

    fun loadTasksByDate (date : Long){
        currentDate = date
        viewModelScope.launch {
            val tasks = dao.getAllTasksByDate(date)
            _tasksList.value = tasks
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            dao.deleteTask(task)
            loadTasksByDate(currentDate)
        }
    }

    fun markTaskAsDone(task: Task) {
        viewModelScope.launch {
            task.isDone = true
            dao.updateTask(task)
            val updatedTasks = dao.getAllTasksByDate(currentDate)
            _tasksList.value = updatedTasks
        }
    }
}