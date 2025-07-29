package com.aydin.foodcalorietracker.service

import com.aydin.foodcalorietracker.model.Food
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/atilsamancioglu/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodAPI::class.java)

    suspend fun getData() : List<Food> = retrofit.getFood()
}