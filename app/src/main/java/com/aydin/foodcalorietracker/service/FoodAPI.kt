package com.aydin.foodcalorietracker.service

import com.aydin.foodcalorietracker.model.Food
import retrofit2.http.GET

interface FoodAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/refs/heads/master/besinler.json

    //Base URL -> https://raw.githubusercontent.com/atilsamancioglu/
    // Endpoint -> BTK20-JSONVeriSeti/refs/heads/master/besinler.json
    @GET("BTK20-JSONVeriSeti/refs/heads/master/besinler.json")
    suspend fun getFood() : List<Food>
}