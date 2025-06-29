package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.models.dao.TaskDao
import com.example.todo.database.models.entity.Task
import javax.inject.Inject


@Database(entities = [Task ::class],version = 1, exportSchema = false)
abstract class  MyDatabase : RoomDatabase(){
    abstract fun taskDao() : TaskDao
}