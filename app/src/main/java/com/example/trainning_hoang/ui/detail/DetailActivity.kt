package com.example.trainning_hoang.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.navArgs
import com.example.trainning_hoang.R
import com.example.trainning_hoang.adapter.DetailViewPagerAdapter
import com.example.trainning_hoang.data.database.model.FavoriteRecipe
import com.example.trainning_hoang.databinding.ActivityDetailBinding
import com.example.trainning_hoang.util.Constant.BUNDLE_KEY
import com.example.trainning_hoang.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val args by navArgs<DetailActivityArgs>()

    private lateinit var menuItem: MenuItem

    private var recipeIsSaved = false

    private var savedRecipeId = 0

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.apply {
            val resultBundle = bundleOf(BUNDLE_KEY to args.result)
            viewPager2.adapter = DetailViewPagerAdapter(this@DetailActivity, resultBundle)
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.over_view)
                    1 -> tab.text = getString(R.string.ingredients)
                    else -> tab.text = getString(R.string.instruction)
                }
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu.findItem(R.id.favorite_recipe)
        checkFavoriteRecipe()
        return true
    }

    private fun checkFavoriteRecipe() {
        mainViewModel.favoriteRecipes.observe(this) { recipes ->
            recipes?.let {
                recipes.forEach {
                    if (it.result == args.result) {
                        recipeIsSaved = true
                        savedRecipeId = it.id
                        changeFavoriteIcon(R.drawable.ic_checked_favorite)
                        return@observe
                    }
                }
            }
            changeFavoriteIcon(R.drawable.ic_unchecked_favorite)
            recipeIsSaved = false
        }
    }

    private fun changeFavoriteIcon(checkedFavoriteIcon: Int) {
        menuItem.icon = ContextCompat.getDrawable(this, checkedFavoriteIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            actionSaveFavoriteRecipe()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionSaveFavoriteRecipe() {
        if (recipeIsSaved) {
            mainViewModel.deleteFavoriteRecipe(FavoriteRecipe(savedRecipeId, args.result))
            Toast.makeText(this, getString(R.string.recipe_unsaved), Toast.LENGTH_SHORT)
                .show()
        } else {
            mainViewModel.insertFavoriteRecipe(FavoriteRecipe(0, args.result))
            Toast.makeText(this, getString(R.string.recipe_saved), Toast.LENGTH_SHORT)
                .show()
        }

    }
}