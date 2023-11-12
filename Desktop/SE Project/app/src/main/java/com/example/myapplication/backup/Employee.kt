package com.example.myapplication.backup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class employee(
    @PrimaryKey
    val id: Int,
    var name: String,
    val age: String,
    val salary: Double,
    val FA: String,
    val OTB: Double,
    val TA: String,
    val EPF: Int,
    val position: String,
    val pwd: String,
    val manager: String,
    val status:String,
    val active:String
    )