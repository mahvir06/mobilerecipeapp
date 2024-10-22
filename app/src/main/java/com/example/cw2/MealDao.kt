package com.example.cw2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cw2.Meal

@Dao
interface MealDao {
    @Query("Select * from meal")
    suspend fun getAll(): List<Meal>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(vararg meal: Meal)
    @Insert
    suspend fun insertAll(vararg meals: Meal)

    @Query("DELETE FROM Meal")
    suspend fun clearTable()

    @Query("SELECT * FROM Meal WHERE name LIKE '%' || :term || '%' OR ingredients LIKE '%' || :term || '%'")

    suspend fun findByName(term: String): List<Meal>
}