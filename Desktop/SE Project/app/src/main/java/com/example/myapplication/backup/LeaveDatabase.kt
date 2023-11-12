package com.example.myapplication.backup

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.myapplication.UriTypeConverter

@Database(entities = [leave::class], version = 7 )
@TypeConverters(UriTypeConverter::class) // Add the type converter here
abstract class LeaveDatabase : RoomDatabase() {
    abstract fun LeaveDao(): LeaveDao

    companion object {
        @Volatile
        private var INSTANCE: LeaveDatabase? = null

        fun getDatabase(context: Context): LeaveDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LeaveDatabase::class.java,
                    "recipe_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}




