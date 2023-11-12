package com.example.myapplication.backup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LeaveDao {
    @Query("SELECT * FROM leave")
    fun getAllEmLeave(): List<leave>

    @Insert
    fun insertLeave(recipe: leave)

    @Query("UPDATE leave SET status= :emStatus WHERE name = :emName and  startDate= :emStartDate")
    fun updateLeaveStatus(emName:String,emStartDate:String,emStatus:String)

    @Query("UPDATE leave SET name= :emName, leaveType= :emLeaveType, BF= :emBF, expired= :emExpired, entitled= :emEntitled,  taken= :emTaken, credited= :emCredited,balance= :emBalance, reason= :emReason, startDate= :emStartDate, endDate= :emEndDate, halfDay= :emHalfDay, status= :emStatus WHERE id = :recipeId")
    fun updateLeave(recipeId: Int,emName:String,emLeaveType:String,emBF:String,emExpired:String,emEntitled:String,emTaken:String,emCredited:String,emBalance:String,emReason:String,emStartDate:String,emEndDate:String,emHalfDay:String,emStatus:String)

    @Query("DELETE FROM leave WHERE id = :recipeId")
    fun deleteLeave(recipeId: Int)
}


