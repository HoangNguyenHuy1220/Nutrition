package com.example.trainning_hoang.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainning_hoang.adapter.FavoriteAdapter
import com.example.trainning_hoang.databinding.FragmentFavoriteRecipesBinding
import com.example.trainning_hoang.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    @Inject lateinit var mainViewModel: MainViewModel

    private lateinit var binding: FragmentFavoriteRecipesBinding

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.favoriteRecipes.observe(viewLifecycleOwner) { recipes ->
            favoriteAdapter.submitList(recipes)
            binding.isEmpty = recipes.isEmpty()
        }
    }

    private fun setUpRecyclerView() {
        favoriteAdapter = FavoriteAdapter(requireActivity(), mainViewModel)
        binding.recyclerFavoriteRecipes.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteAdapter.closeContextualActionMode()
    }
}