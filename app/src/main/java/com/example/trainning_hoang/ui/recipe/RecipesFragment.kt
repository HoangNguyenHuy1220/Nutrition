package com.example.trainning_hoang.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainning_hoang.adapter.RecipeAdapter
import com.example.trainning_hoang.databinding.FragmentRecipesBinding
import com.example.trainning_hoang.util.NetworkResult
import com.example.trainning_hoang.viewmodel.MainViewModel
import com.example.trainning_hoang.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var binding: FragmentRecipesBinding

    private lateinit var adapter: RecipeAdapter

    private val args by navArgs<RecipesFragmentArgs>()

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
        }
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadRecipes()
        requestDataApi()
        binding.btOrder.setOnClickListener {
            findNavController().navigate(
                RecipesFragmentDirections.actionRecipesFragmentToRecipeBottomSheet()
            )
        }
    }

    private fun requestDataApi() {
        val queryMap = recipeViewModel.applyQueries(args.mealType, args.dietType)
        mainViewModel.getRecipes(queryMap)
    }

    private fun loadRecipes() {
        mainViewModel.recipeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { foodRecipe ->
                        adapter.submitList(foodRecipe.results)
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromLocal()
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun loadDataFromLocal() {
        mainViewModel.localRecipes.observe(viewLifecycleOwner) { recipes ->
            if (!recipes.isNullOrEmpty()) {
                adapter.submitList(recipes)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            adapter = RecipeAdapter()
            recyclerFood.adapter = adapter
            recyclerFood.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}