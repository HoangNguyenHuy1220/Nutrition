package com.example.trainning_hoang.ui.mealplan

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trainning_hoang.R
import com.example.trainning_hoang.adapter.AddNewMealPlanAdapter
import com.example.trainning_hoang.data.database.model.FoodRecipe
import com.example.trainning_hoang.data.database.model.MealPlanRecipe
import com.example.trainning_hoang.data.database.model.Result
import com.example.trainning_hoang.databinding.FragmentAddMealPlanRecipesBinding
import com.example.trainning_hoang.util.NetworkResult
import com.example.trainning_hoang.viewmodel.MainViewModel
import com.example.trainning_hoang.viewmodel.RecipeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddMealPlanRecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentAddMealPlanRecipesBinding

    private lateinit var recipeAdapter: AddNewMealPlanAdapter

    private lateinit var favoriteRecipes: List<Result>

    private lateinit var selectedRecipes: List<Result>

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMealPlanRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setUpRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            favoriteRecipes = recipes.map {
                it.result
            }
            reloadRecyclerData(favoriteRecipes)
        }

        mainViewModel.searchRespone.observe(viewLifecycleOwner) { response ->
            searchDataApi(response)
        }

        binding.btApplyRecipes.setOnClickListener {
            chooseDayPlan()
        }
    }

    private fun chooseDayPlan() {
        val daysOfWeek = resources.getStringArray(R.array.days_of_week)
        val daysOfPlan = mutableListOf<String>()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.choose_plan_dialog_title))
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                actionSaveMealPlan(daysOfPlan)
            }
            .setMultiChoiceItems(daysOfWeek, null) { _, position, isChecked ->
                if (isChecked) {
                    daysOfPlan.add(daysOfWeek[position])
                } else {
                    daysOfPlan.remove(daysOfWeek[position])
                }
            }
            .show()
    }

    private fun actionSaveMealPlan(daysOfPlan: MutableList<String>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val mealPlanList = selectedRecipes.map { recipe ->
                MealPlanRecipe(result = recipe, planDays = daysOfPlan)
            }
            mainViewModel.insertMealPlanRecipes(mealPlanList)
        }
        findNavController().navigateUp()
    }

    private fun setUpRecyclerView() {
        recipeAdapter = AddNewMealPlanAdapter { recipes ->
            if (recipes.isNotEmpty()) {
                selectedRecipes = recipes
                binding.btApplyRecipes.apply {
                    visibility = View.VISIBLE
                    text = context.resources.getQuantityString(
                        R.plurals.item_selected,
                        recipes.size,
                        recipes.size
                    )
                }
            } else {
                binding.btApplyRecipes.visibility = View.GONE
            }
        }
        binding.recyclerRecipes.apply {
            adapter = recipeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun reloadRecyclerData(recipes: List<Result>) {
        recipeAdapter.submitList(recipes)
        binding.btApplyRecipes.visibility = View.GONE
        recipeAdapter.clearSelectedItems()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = getString(R.string.new_recipe)
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        actionSearch(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        actionSearch(query)
        return true
    }

    private fun actionSearch(query: String?) {
        if (!query.isNullOrEmpty()) {
            recipeAdapter.submitList(emptyList())
            val queryMap = recipeViewModel.applySearchQueries(query)
            mainViewModel.searchRecipes(queryMap)
        } else {
            reloadRecyclerData(favoriteRecipes)
            binding.tvSavedRecipes.visibility = View.VISIBLE
        }
    }

    private fun searchDataApi(response: NetworkResult<FoodRecipe>) {
        if (response is NetworkResult.Success) {
            response.data?.let {
                reloadRecyclerData(it.results)
                binding.tvSavedRecipes.visibility = View.GONE
            }
        }

    }

}