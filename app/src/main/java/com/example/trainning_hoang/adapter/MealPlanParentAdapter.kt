package com.example.trainning_hoang.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainning_hoang.R
import com.example.trainning_hoang.data.database.model.MealPlanRecipe
import com.example.trainning_hoang.databinding.ParentRowMealPlanLayoutBinding
import com.example.trainning_hoang.viewmodel.MainViewModel

class MealPlanParentAdapter(
    private val context: Context,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<MealPlanParentAdapter.ParentViewHolder>() {

    private val daysOfWeek = context.resources.getStringArray(R.array.days_of_week)

    private val mealPlanList = Array(7) { mutableListOf<MealPlanRecipe>() }

    @SuppressLint("NotifyDataSetChanged")
    fun setMealPlanData(dataList: List<MealPlanRecipe>) {
        dataList.forEach { recipe ->
            if (recipe.planDays.contains(daysOfWeek[0]))
                mealPlanList[0].add(recipe)
            if (recipe.planDays.contains(daysOfWeek[1]))
                mealPlanList[1].add(recipe)
            if (recipe.planDays.contains(daysOfWeek[2]))
                mealPlanList[2].add(recipe)
            if (recipe.planDays.contains(daysOfWeek[3]))
                mealPlanList[3].add(recipe)
            if (recipe.planDays.contains(daysOfWeek[4]))
                mealPlanList[4].add(recipe)
            if (recipe.planDays.contains(daysOfWeek[5]))
                mealPlanList[5].add(recipe)
            if (recipe.planDays.contains(daysOfWeek[6]))
                mealPlanList[6].add(recipe)
        }
        notifyDataSetChanged()
    }

    inner class ParentViewHolder(val binding: ParentRowMealPlanLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(day: String, mealPlanList: List<MealPlanRecipe>) {
            binding.apply {
                dayOfWeek = day
                val adapter = MealPlanAdapter(mainViewModel)
                recyclerMealPlanChildren.adapter = adapter
                recyclerMealPlanChildren.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter.submitList(mealPlanList)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ParentRowMealPlanLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.onBind(daysOfWeek[position], mealPlanList[position])
        if (position == 6) {
            holder.binding.viewLine.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mealPlanList.size
    }
}