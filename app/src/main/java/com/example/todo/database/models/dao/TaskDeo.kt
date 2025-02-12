package com.example.todo.database.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.database.models.entity.Task

@Dao
interface TaskDeo {
    @Insert
    fun insertNewTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from tasks")
    fun getAllTasks():List<Task>

    @Query("select * from tasks where date = :date")
    fun getAllTasksByDate(date:Long):List<Task>

    @Query("select * from tasks where id = :id")
    fun getTaskById(id:Int): Task?

    @Query("select * from tasks where isDone = 0")
    fun getUnCompletedTasks():List<Task>
}