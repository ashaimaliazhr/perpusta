package com.skripsi.perpusta.model.task

data class Task(
    val title: String,
    val hour: String,
    val date: String,
    val reminderTime: Long,
    val userId: String,
    val id: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}
