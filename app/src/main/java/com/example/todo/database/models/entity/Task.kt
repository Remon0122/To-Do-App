package com.example.todo.database.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    var title:String,
    var description:String? = null,
    var date:Long,
    var time:Long,
    var isDone :Boolean = false,
)
