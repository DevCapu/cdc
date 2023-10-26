package br.com.devcapu.cdc

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Author(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis()
)