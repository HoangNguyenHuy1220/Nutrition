package com.example.trainning_hoang.adapter

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.example.trainning_hoang.R
import com.example.trainning_hoang.adapter.baseadapter.RecyclerAdapter
import com.example.trainning_hoang.data.database.model.FavoriteRecipe
import com.example.trainning_hoang.ui.favorite.FavoriteRecipesFragmentDirections
import com.example.trainning_hoang.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteAdapter(
    private val fragmentActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerAdapter<FavoriteRecipe>(DiffCallBack), ActionMode.Callback {

    private lateinit var mActionMode: ActionMode

    private val selectedList = mutableListOf<FavoriteRecipe>()

    private val listViewHolder = mutableListOf<ViewHolder>()

    private var multiSelection = false

    object DiffCallBack : DiffUtil.ItemCallback<FavoriteRecipe>() {
        override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item.result)
        if (selectedList.contains(item)) {
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.cardStrokeColor)
        }
        listViewHolder.add(holder)

        /** Single click listener */
        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, item)
            } else {
                holder.itemView.findNavController().navigate(
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(
                        item.result
                    )
                )
            }
        }

        /** Long click listener */
        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                fragmentActivity.startActionMode(this)
                applySelection(holder, item)
                true
            } else {
                applySelection(holder, item)
                true
            }
        }
    }

    private fun applySelection(holder: ViewHolder, currentRecipe: FavoriteRecipe) {
        if (selectedList.contains(currentRecipe)) {
            if (selectedList.size == 1) {
                mActionMode.finish()
            }
            selectedList.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.white, R.color.lightMediumGray)
        } else {
            selectedList.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.cardStrokeColor)
        }
        applyActionModeTitle()
    }

    private fun applyActionModeTitle() {
        when (selectedList.size) {
            0 -> mActionMode.finish()
            else -> mActionMode.title =
                fragmentActivity.resources.getQuantityString(
                    R.plurals.item_selected,
                    selectedList.size,
                    selectedList.size
                )
        }
    }

    private fun changeRecipeStyle(holder: ViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.cardView.apply {
            setCardBackgroundColor(
                ContextCompat.getColor(fragmentActivity, backgroundColor)
            )
            setStrokeColor(
                ContextCompat.getColor(fragmentActivity, strokeColor)
            )
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        mActionMode = actionMode!!
        multiSelection = true
        applyStatusBarColor(R.color.darker)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_recipe) {
            actionDeleteSelectedRecipes()
        } else {
            actionSelectAll()
        }

        return true
    }

    private fun actionSelectAll() {
        if (selectedList.size == currentList.size) {
            mActionMode.finish()
            return
        }
        currentList.forEach {
            if (!selectedList.contains(it)) {
                selectedList.add(it)
            }
        }
        listViewHolder.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.cardStrokeColor)
        }
        applyActionModeTitle()
    }

    private fun actionDeleteSelectedRecipes() {
        val removeList = mutableListOf<FavoriteRecipe>()
        selectedList.forEach {
            mainViewModel.deleteFavoriteRecipe(it)
            removeList.add(it)
        }

        Snackbar.make(
            fragmentActivity.findViewById(R.id.delete_favorite_recipe),
            fragmentActivity.resources.getString(R.string.remove_item_notify),
            Snackbar.LENGTH_LONG
        )
            .setAction(fragmentActivity.resources.getString(R.string.restore)) {
                mainViewModel.insertRecipeList(removeList)
            }
            .show()
        mActionMode.finish()
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        applyStatusBarColor(R.color.colorPrimaryVariant)
        multiSelection = false
        listViewHolder.forEach {
            changeRecipeStyle(it, R.color.white, R.color.lightMediumGray)
        }
        selectedList.clear()
    }

    private fun applyStatusBarColor(color: Int) {
        fragmentActivity.window.statusBarColor = ContextCompat.getColor(fragmentActivity, color)
    }

    fun closeContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

}