package com.example.trainning_hoang.util.binding_adapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.trainning_hoang.R
import com.example.trainning_hoang.util.Constant.BASE_IMAGE_URL
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("food_attribute")
fun MaterialTextView.bindFoodAttribute(attribute: Boolean?) {
    attribute?.let {
        setCompoundDrawablesWithIntrinsicBounds(
            if (it) ContextCompat.getDrawable(context, R.drawable.ic_checked)
            else ContextCompat.getDrawable(context, R.drawable.ic_check_mark),
            null,
            null,
            null
        )
    }
}

@BindingAdapter("ingredient_image")
fun ImageView.bindIngredientImage(imgPath: String?) {
    if (imgPath != null) {
        val imgUrl = BASE_IMAGE_URL + imgPath
        load(imgUrl) {
            crossfade(600)
            error(R.drawable.food_placeholder)
        }
    } else {
        setImageResource(R.drawable.food_placeholder)
    }
}