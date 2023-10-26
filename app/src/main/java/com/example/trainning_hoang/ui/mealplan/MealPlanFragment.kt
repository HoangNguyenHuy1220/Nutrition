package com.example.trainning_hoang.ui.mealplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainning_hoang.adapter.MealPlanParentAdapter
import com.example.trainning_hoang.databinding.FragmentMealPlanBinding
import com.example.trainning_hoang.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MealPlanFragment : Fragment() {

    private lateinit var binding: FragmentMealPlanBinding

    private lateinit var mealPlanAdapter: MealPlanParentAdapter

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealPlanBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.mealPlanRecipes.observe(viewLifecycleOwner) { mealPlanRecipes ->
            mealPlanRecipes?.let { dataList ->
                mealPlanAdapter.setMealPlanData(dataList)
            }
        }
        binding.btAddMealPlan.setOnClickListener {
            findNavController().navigate(
                MealPlanFragmentDirections.actionMealPlanFragmentToAddNewRecipesFragment()
            )
        }
    }

    private fun setUpRecyclerView() {
        mealPlanAdapter = MealPlanParentAdapter(requireContext(), mainViewModel)
        binding.apply {
            recyclerMealPlanParent.adapter = mealPlanAdapter
            recyclerMealPlanParent.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}