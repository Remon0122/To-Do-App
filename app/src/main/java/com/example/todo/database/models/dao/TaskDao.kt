package com.example.todo.database.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.database.models.entity.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertNewTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("select * from task")
    suspend fun getAllTasks():List<Task>

    @Query("select * from task where date = :date")
    suspend fun getAllTasksByDate(date:Long):List<Task>

    @Query("select * from task where id = :id")
    suspend fun getTaskById(id:Int): Task?

    @Query("select * from task where isDone = 0")
   suspend fun getUnCompletedTasks():List<Task>

}