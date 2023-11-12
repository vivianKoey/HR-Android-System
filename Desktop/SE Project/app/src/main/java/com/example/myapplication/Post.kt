package com.example.myapplication.backup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    var postTime:String,
    var postTitle:String,
    var postContent:String
    )