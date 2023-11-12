package com.example.myapplication.backup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class employeeRecord(
    val id: Int,
    var name: String
    )