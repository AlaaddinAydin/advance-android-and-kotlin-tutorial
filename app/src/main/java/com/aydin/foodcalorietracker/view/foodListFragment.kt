package com.aydin.foodcalorietracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.aydin.foodcalorietracker.adapter.FoodRecyclerAdapter
import com.aydin.foodcalorietracker.databinding.FragmentFoodListBinding
import com.aydin.foodcalorietracker.service.FoodAPI
import com.aydin.foodcalorietracker.viewmodel.FoodListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class foodListFragment : Fragment() {

    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FoodListViewModel
    private var foodRecyclerAdapter = FoodRecyclerAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        viewModel.refreshData()

        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.foodRecyclerView.adapter = foodRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.foodRecyclerView.visibility = View.GONE
            binding.foodErrorMessage.visibility = View.GONE
            binding.foodprogressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observData()
    }

    private fun observData(){
        viewModel.foods.observe(viewLifecycleOwner) {
            // Adapter
            foodRecyclerAdapter.updateFoodList(it)
            binding.foodRecyclerView.visibility = View.VISIBLE
        }

        viewModel.foodLoading.observe(viewLifecycleOwner) {
            if(it){
                binding.foodErrorMessage.visibility = View.GONE
                binding.foodprogressBar.visibility = View.VISIBLE
            } else {
                binding.foodErrorMessage.visibility = View.GONE
                binding.foodprogressBar.visibility = View.GONE
            }
        }

        viewModel.foodErrorMessasge.observe(viewLifecycleOwner) {
            if (it){
                binding.foodErrorMessage.visibility = View.VISIBLE
                binding.foodprogressBar.visibility = View.GONE
            } else {
                binding.foodErrorMessage.visibility = View.GONE
                binding.foodprogressBar.visibility = View.GONE
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}