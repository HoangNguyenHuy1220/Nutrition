package com.example.trainning_hoang.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.trainning_hoang.ui.detail.IngredientsFragment
import com.example.trainning_hoang.ui.detail.InstructionFragment
import com.example.trainning_hoang.ui.detail.OverviewFragment
import com.example.trainning_hoang.util.Constant.NUM_PAGES


class DetailViewPagerAdapter(fa: FragmentActivity, private val resultBundle: Bundle) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = OverviewFragment()
                fragment.arguments = resultBundle
                fragment
            }
            1 -> {
                val fragment = IngredientsFragment()
                fragment.arguments = resultBundle
                fragment
            }
            else -> {
                val fragment = InstructionFragment()
                fragment.arguments = resultBundle
                fragment
            }
        }
    }
}