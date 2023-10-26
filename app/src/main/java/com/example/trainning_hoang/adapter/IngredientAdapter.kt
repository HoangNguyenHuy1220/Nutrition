package com.example.trainning_hoang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainning_hoang.databinding.IngredientRowLayoutBinding
import com.example.trainning_hoang.data.database.model.Ingredient

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private lateinit var ingredientList: List<Ingredient>

    fun setIngredientData(dataList: List<Ingredient>) {
        ingredientList = dataList
    }

    class IngredientViewHolder(private val binding: IngredientRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(ingredientItem: Ingredient) {
            binding.apply {
                ingredient = ingredientItem
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            IngredientRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.onBind(ingredient)
    }
}