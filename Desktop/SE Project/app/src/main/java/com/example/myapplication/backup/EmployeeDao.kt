package com.example.myapplication.backup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee ")
    fun getAllRecipes(): List<employee>

    @Query("SELECT * FROM employee WHERE active= :emActive")
    fun getActiveUser(emActive:String): List<employee>

    @Insert
    fun insertRecipe(recipe: employee)

    @Query("UPDATE employee SET active= :emActive WHERE name = :emName")
    fun updateActive(emName:String,emActive:String)


    @Query("UPDATE employee SET name= :emName, age= :emAge, salary= :emSalary, fa= :emFA, otb= :emOTB,  ta= :emTA, epf= :emEPF,position= :emPosition, pwd= :emPwd, manager= :emManager, status= :emStatus, active= :emActive WHERE id = :recipeId")
    fun updateRecipe(recipeId: Int,emName:String,emAge:String,emSalary:String,emFA:String,emOTB:String,emTA:String,emEPF:String,emPosition:String,emPwd:String,emManager:String,emStatus:String,emActive:String)

    @Query("DELETE FROM employee WHERE id = :recipeId")
    fun deleteRecipe(recipeId: Int)
}


