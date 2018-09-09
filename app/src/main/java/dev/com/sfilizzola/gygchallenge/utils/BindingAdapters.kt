package dev.com.sfilizzola.gygchallenge.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.RatingBar
import dev.com.sfilizzola.gygchallenge.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isFavorite")
    fun setFavoriteImg(view: ImageView, favorite:Boolean){
        if (favorite)
            view.setBackgroundResource(R.drawable.ic_heart)
        else
            view.setBackgroundResource(R.drawable.ic_heart_outline)
    }


    @JvmStatic
    @BindingAdapter("ratingValue")
    fun setFavoriteImg(view: RatingBar, rating:String){
        view.rating = rating.toFloat()
    }
}