package com.aydin.foodcalorietracker.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aydin.foodcalorietracker.model.Food
import com.aydin.foodcalorietracker.roomdb.FoodDatabase
import com.aydin.foodcalorietracker.service.FoodAPIService
import com.aydin.foodcalorietracker.util.SpecialSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodListViewModel(application: Application) : AndroidViewModel(application){

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessasge = MutableLiveData<Boolean>();
    val foodLoading = MutableLiveData<Boolean>();

    private val foodApiService = FoodAPIService()
    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())

    private val updateTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData(){
        val saveTime = specialSharedPreferences.getTime()

        if(saveTime != null && saveTime != 0L && System.nanoTime() - saveTime < updateTime){
            // 10 Dakika daha geçmedi Roomdan verileri al
            getDataFromRoom()
        } else {
            getDataFromInternet()
        }
    }

    fun refreshDataFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromRoom(){
        foodLoading.value = true
        viewModelScope.launch (Dispatchers.IO) {
            val foodlist = FoodDatabase(getApplication()).foodDao().getAll()
            //hangi layerda çalışıcağına dikkat et
            withContext(Dispatchers.Main){
                showFoods(foodlist)
                Toast.makeText(getApplication(), "Besinleri Roomdan Aldık", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDataFromInternet(){
        foodLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val foodList = foodApiService.getData()
            withContext(Dispatchers.Main){
                foodLoading.value = false
                saveDataToRoom(foodList)
                Toast.makeText(getApplication(), "Besinleri İnternetten Aldık", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFoods(foodlist : List<Food>){
        foods.value = foodlist
        foodLoading.value = false
        foodErrorMessasge.value = false
    }

    private fun saveDataToRoom(foodList: List<Food>){
        viewModelScope.launch(Dispatchers.IO) {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAll()
            val uuidList = dao.insertAll(*foodList.toTypedArray())
            var i = 0
            while (i < foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i = i + 1
            }
            withContext(Dispatchers.Main) {
                showFoods(foodList)
            }
        }

        specialSharedPreferences.setTime(System.nanoTime())
    }
}