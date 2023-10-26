package com.example.trainning_hoang.adapter

import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.example.trainning_hoang.adapter.baseadapter.MealPlanChildrenAdapter
import com.example.trainning_hoang.data.database.model.MealPlanRecipe
import com.example.trainning_hoang.ui.mealplan.MealPlanFragmentDirections
import com.example.trainning_hoang.viewmodel.MainViewModel

class MealPlanAdapter(private val mainViewModel: MainViewModel) :
    MealPlanChildrenAdapter<MealPlanRecipe>(DifferCallback) {

    object DifferCallback : DiffUtil.ItemCallback<MealPlanRecipe>() {
        override fun areItemsTheSame(oldItem: MealPlanRecipe, newItem: MealPlanRecipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MealPlanRecipe, newItem: MealPlanRecipe): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val mealPlanRecipe = getItem(position)
        holder.onBind(mealPlanRecipe.result)
        holder.binding.cardView.setOnClickListener {
            holder.itemView.findNavController().navigate(
                MealPlanFragmentDirections.actionMealPlanFragmentToDetailActivity(
                    mealPlanRecipe.result
                )
            )
        }
        holder.binding.imgDelete.setOnClickListener {
            mainViewModel.deleteMealPlanRecipe(mealPlanRecipe)
        }
    }

}