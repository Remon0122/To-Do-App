package com.example.todo.database.models.entity

import androidx.room.Entity
import java.time.LocalDateTime

@Entity("tasks")
data class Task(
    var id: Int? = null,
    var title: String? = null,
    var details: String? = null,
    var date: LocalDateTime? = null,
    var isDone: Boolean = false
)
