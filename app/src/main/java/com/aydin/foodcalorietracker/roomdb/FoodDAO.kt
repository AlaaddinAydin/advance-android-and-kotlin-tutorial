package com.aydin.foodcalorietracker.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aydin.foodcalorietracker.model.Food

@Dao
interface FoodDAO {
    @Query("SELECT * FROM food")
    suspend fun getAll() : List<Food>

    @Query("DELETE FROM food")
    suspend fun deleteAll()

    @Query("SELECT * FROM food WHERE uuid = :foodId")
    suspend fun getFoodById(foodId : Int) : Food

    @Insert
    suspend fun insertAll(vararg food: Food) : List<Long>
    //Eklediğin besinlerin ID'lerini long olarak geri veriyor.
}