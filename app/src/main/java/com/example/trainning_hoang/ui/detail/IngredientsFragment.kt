package com.example.trainning_hoang.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainning_hoang.adapter.IngredientAdapter
import com.example.trainning_hoang.databinding.FragmentIngredientsBinding
import com.example.trainning_hoang.data.database.model.Result
import com.example.trainning_hoang.util.Constant.BUNDLE_KEY

class IngredientsFragment : Fragment() {

    private lateinit var binding: FragmentIngredientsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerIngredient()
    }

    private fun setUpRecyclerIngredient() {
        val ingredientList = arguments!!.getParcelable<Result>(BUNDLE_KEY)!!.extendedIngredients
        val adapter = IngredientAdapter()
        binding.recyclerIngredients.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerIngredients.adapter = adapter
        adapter.setIngredientData(ingredientList)
    }

}