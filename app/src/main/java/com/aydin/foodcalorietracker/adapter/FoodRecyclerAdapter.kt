package com.aydin.foodcalorietracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aydin.foodcalorietracker.databinding.FoodRecyclerRowBinding
import com.aydin.foodcalorietracker.model.Food
import com.aydin.foodcalorietracker.view.foodListFragmentDirections

class FoodRecyclerAdapter(val foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodRecyclerViewHolder>(){

    class FoodRecyclerViewHolder(val binding: FoodRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecyclerViewHolder {
        val binding = FoodRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun updateFoodList(newFoodList : List<Food>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FoodRecyclerViewHolder, position: Int) {
        holder.binding.foodNameRow.text = foodList[position].isim
        holder.binding.foodCalorieRow.text = foodList[position].kalori

        holder.itemView.setOnClickListener {
            val action = foodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }
}