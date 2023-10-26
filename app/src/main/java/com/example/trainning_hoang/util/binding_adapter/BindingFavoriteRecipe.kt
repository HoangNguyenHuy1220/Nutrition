package com.example.trainning_hoang.util.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("isEmpty")
fun MaterialTextView.bindIsEmptyRecipes(isEmpty: Boolean) {
    visibility =
        if (isEmpty) View.VISIBLE
        else View.GONE
}