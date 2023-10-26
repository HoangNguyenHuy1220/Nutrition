package com.example.trainning_hoang.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.trainning_hoang.data.MealAndDietType
import com.example.trainning_hoang.databinding.FragmentRecipeBottomSheetBinding
import com.example.trainning_hoang.util.Constant.DEFAULT_DIET_TYPE
import com.example.trainning_hoang.util.Constant.DEFAULT_MEAL_TYPE
import com.example.trainning_hoang.viewmodel.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRecipeBottomSheetBinding

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    private var mealType = DEFAULT_MEAL_TYPE
    private var mealTypeId = 0
    private var dietType = DEFAULT_DIET_TYPE
    private var dietTypeId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeViewModel.mealAndDietType.asLiveData().observe(viewLifecycleOwner) {
            mealType = it.selectedMealType
            dietType = it.selectedDietType
            updateChip(it)
        }

        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            mealType = group.findViewById<Chip>(checkedId).text.toString().lowercase()
            mealTypeId = checkedId
        }
        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            dietType = group.findViewById<Chip>(checkedId).text.toString().lowercase()
            dietTypeId = checkedId
        }
        binding.btApply.setOnClickListener {
            recipeViewModel.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
            findNavController().navigate(
                RecipeBottomSheetDirections.actionRecipeBottomSheetToRecipesFragment(
                    mealType,
                    dietType
                )
            )
        }
    }

    private fun updateChip(mealAndDietType: MealAndDietType) {
        binding.apply {
            if (mealAndDietType.selectedMealTypeKey != 0) {
                mealTypeChipGroup.findViewById<Chip>(mealAndDietType.selectedMealTypeKey).isChecked =
                    true
            }
            if (mealAndDietType.selectedDietTypeKey != 0) {
                dietTypeChipGroup.findViewById<Chip>(mealAndDietType.selectedDietTypeKey).isChecked =
                    true
            }

        }
    }


}