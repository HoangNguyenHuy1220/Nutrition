package com.example.trainning_hoang.util.binding_adapter

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.trainning_hoang.R
import com.example.trainning_hoang.data.database.model.FoodRecipe
import com.example.trainning_hoang.util.NetworkResult
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textview.MaterialTextView
import org.jsoup.Jsoup

@BindingAdapter("foodImage")
fun ImageView.bindFoodImage(imageUrl: String?) {
    if (imageUrl != null) {
        load(imageUrl) {
            crossfade(600)
            error(R.drawable.food_placeholder)
        }
    } else {
        setImageResource(R.drawable.food_placeholder)
    }
}

@BindingAdapter("numberOfLike")
fun MaterialTextView.bindNumberOfLike(likes: Int) {
    text = likes.toString()
}

@BindingAdapter("readyInMinute")
fun MaterialTextView.bindReadyInMinute(minutes: Int) {
    text = minutes.toString()
}

@BindingAdapter("vegan")
fun MaterialTextView.bindVegan(isVegan: Boolean) {
    if (isVegan) {
        setCompoundDrawablesWithIntrinsicBounds(
            null,
            ContextCompat.getDrawable(context, R.drawable.ic_leaf),
            null,
            null
        )
        setTextColor(ContextCompat.getColor(context, R.color.green))
    } else {
        setCompoundDrawablesWithIntrinsicBounds(
            null,
            ContextCompat.getDrawable(context, R.drawable.ic_no_leaf),
            null,
            null
        )
        setTextColor(ContextCompat.getColor(context, R.color.lightMediumGray))
    }
}

@BindingAdapter("parseHtmlText")
fun MaterialTextView.parseHtmlText(description: String?) {
    description?.let {
        text = Jsoup.parse(it).text()
    }
}

@BindingAdapter("loadErrorImage")
fun MaterialTextView.bindErrorImage(response: NetworkResult<FoodRecipe>?) {
    response?.let {
        visibility =
            if (response is NetworkResult.Error) View.VISIBLE
            else View.GONE
    }
}

@BindingAdapter("loadShimmer")
fun ShimmerFrameLayout.loadShimmer(response: NetworkResult<FoodRecipe>?) {
    response?.let {
        visibility = if (response is NetworkResult.Loading) {
            startShimmer()
            View.VISIBLE
        } else {
            stopShimmer()
            View.GONE
        }

    }
}