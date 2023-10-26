package com.example.trainning_hoang.adapter

import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.example.trainning_hoang.adapter.baseadapter.RecyclerAdapter
import com.example.trainning_hoang.data.database.model.Result
import com.example.trainning_hoang.ui.recipe.RecipesFragmentDirections

class RecipeAdapter : RecyclerAdapter<Result>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(
                RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(
                    item
                )
            )
        }
    }

}