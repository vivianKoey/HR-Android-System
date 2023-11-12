package com.example.myapplication.backup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class claim(
    @PrimaryKey
    val id: Int,
    var name: String,
    var desc:String,
    var claimDate:String,
    var claimType:String,
    var amount:String,
    var claimFile:String,
    var claimStatus:String
    )