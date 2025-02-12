package com.example.todo.database.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.models.dao.TaskDao
import com.example.todo.database.models.entity.Task

@Database(entities = [Task ::class],version = 1, exportSchema = true)
abstract class MyDataBase : RoomDatabase(){
    abstract fun taskDao() : TaskDao

    companion object{
        private var myDataBase : MyDataBase ?= null
        private val DATABASE_NAME = "task"

        fun init (applicationContext: Context){
            if (myDataBase == null){
                myDataBase = Room.databaseBuilder(applicationContext, MyDataBase::class.java,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build()
            }
        }
        fun getInstance():MyDataBase{
            return myDataBase !!
        }
    }
}