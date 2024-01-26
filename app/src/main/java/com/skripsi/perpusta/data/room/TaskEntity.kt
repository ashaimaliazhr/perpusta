package com.skripsi.perpusta.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    val title: String,
    val hour: String,
    val date: String,
    val reminderTime: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
