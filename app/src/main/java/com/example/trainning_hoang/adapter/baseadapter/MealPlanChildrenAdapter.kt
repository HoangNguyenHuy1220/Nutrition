package com.example.trainning_hoang.adapter.baseadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainning_hoang.data.database.model.Result
import com.example.trainning_hoang.databinding.ChildrentMealPlanItemLayoutBinding

abstract class MealPlanChildrenAdapter<T>(differCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, MealPlanChildrenAdapter.ChildViewHolder>(differCallback) {

    class ChildViewHolder(val binding: ChildrentMealPlanItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(result: Result) {
            binding.apply {
                recipe = result
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ChildrentMealPlanItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChildViewHolder(binding)
    }

    abstract override fun onBindViewHolder(holder: ChildViewHolder, position: Int)
}