package com.example.myapplication.backup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class leave(
    @PrimaryKey
    val id: Int,
    var name: String,
    var leaveType:String,
    var BF:String,
    var expired:String,
    var entitled:String,
    var taken:String,
    var credited:String,
    var balance:String,
    var reason:String,
    var startDate:String,
    var endDate:String,
    var halfDay:String,
    var status:String
    )