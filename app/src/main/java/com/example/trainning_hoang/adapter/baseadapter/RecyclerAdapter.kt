package com.example.trainning_hoang.adapter.baseadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainning_hoang.data.database.model.Result
import com.example.trainning_hoang.databinding.RecipeRowLayoutBinding

abstract class RecyclerAdapter<T>(differCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerAdapter.ViewHolder>(differCallback) {

    class ViewHolder(val binding: RecipeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(foodItem: Result) {
            binding.apply {
                recipe = foodItem
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecipeRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    abstract override fun onBindViewHolder(holder: ViewHolder, position: Int)
}