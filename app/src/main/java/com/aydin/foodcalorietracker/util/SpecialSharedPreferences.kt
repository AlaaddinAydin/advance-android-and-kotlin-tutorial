package com.aydin.foodcalorietracker.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SpecialSharedPreferences {

    companion object {
        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null
        private var instance : SpecialSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createSpecialSharedPrefecences(context).also {
                instance = it
            }
        }

        private fun createSpecialSharedPrefecences(context: Context) : SpecialSharedPreferences{
            //activity'nin dışından oluşturmak istediğimizde böyle bir şekilde oluşturmak lazım.
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun setTime(time : Long){
        sharedPreferences?.edit()?.putLong(TIME, time)?.apply()
    }

    fun getTime() = sharedPreferences?.getLong(TIME, 0)
}