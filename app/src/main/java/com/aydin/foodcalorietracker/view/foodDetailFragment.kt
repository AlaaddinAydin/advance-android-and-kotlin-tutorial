package com.aydin.foodcalorietracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aydin.foodcalorietracker.databinding.FragmentFoodDetailBinding
import com.aydin.foodcalorietracker.util.downloadImage
import com.aydin.foodcalorietracker.util.makeNewPlaceholder
import com.aydin.foodcalorietracker.viewmodel.FoodDetailViewModel

class foodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var foodDetailViewModel: FoodDetailViewModel
    var selectedFoodId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodDetailViewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]

        arguments?.let {
            selectedFoodId = foodDetailFragmentArgs.fromBundle(it).foodId
        }

        foodDetailViewModel.getFoodDataFromRoom(selectedFoodId)
        observeLiveData()
    }

    private fun observeLiveData() {
        foodDetailViewModel.selectedFoodLiveData.observe(viewLifecycleOwner) {
            binding.foodNameDetail.text = it.isim
            binding.foodCalorieDetail.text = it.kalori
            binding.foodCarbsDetail.text = it.karbonhidrat
            binding.foodProteinDetail.text = it.protein
            binding.foodfatDetail.text = it.yag
            binding.foodImage.downloadImage(it.gorsel, makeNewPlaceholder(requireContext()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}