package com.example.trainning_hoang.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.example.trainning_hoang.R
import com.example.trainning_hoang.adapter.baseadapter.MealPlanChildrenAdapter
import com.example.trainning_hoang.data.database.model.Result

class AddNewMealPlanAdapter(
    private val changeApplyButtonState: (List<Result>) -> Unit
) : MealPlanChildrenAdapter<Result>(DifferCallback) {

    private val selectedItems = mutableListOf<Result>()

    object DifferCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val result = getItem(position)
        holder.onBind(result)

        holder.binding.apply {
            imgDelete.visibility = View.GONE
            cardView.setOnClickListener {
                applyItemSelection(result, holder)
            }
        }
    }

    private fun applyItemSelection(item: Result, holder: ChildViewHolder) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
            changeRecipeStyle(holder, R.color.white, R.color.lightMediumGray)
        } else {
            selectedItems.add(item)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        }
        changeApplyButtonState(selectedItems)
    }

    private fun changeRecipeStyle(
        holder: ChildViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        holder.binding.cardView.apply {
            setCardBackgroundColor(
                ContextCompat.getColor(context, backgroundColor)
            )
            setStrokeColor(
                ContextCompat.getColor(context, strokeColor)
            )
        }
    }

    fun clearSelectedItems() = selectedItems.clear()
}