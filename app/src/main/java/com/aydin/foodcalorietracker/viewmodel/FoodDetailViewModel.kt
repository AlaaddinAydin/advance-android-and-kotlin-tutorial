package com.aydin.foodcalorietracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aydin.foodcalorietracker.model.Food
import com.aydin.foodcalorietracker.roomdb.FoodDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDetailViewModel(application: Application) : AndroidViewModel(application){

    val selectedFoodLiveData = MutableLiveData<Food>()

    fun getFoodDataFromRoom (id : Int) {
        val dao = FoodDatabase(getApplication()).foodDao()
        viewModelScope.launch (Dispatchers.IO) {
            val food = dao.getFoodById(id)
            withContext(Dispatchers.Main){
                selectedFoodLiveData.value = food
            }
        }
    }
}