package com.aydin.foodcalorietracker.roomdb

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aydin.foodcalorietracker.model.Food
import kotlin.concurrent.Volatile

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDAO

    companion object{

        @Volatile
        private var instance : FoodDatabase? = null

        private var lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FoodDatabase::class.java,
            "FoodDatabase"
        ).build()
    }
}