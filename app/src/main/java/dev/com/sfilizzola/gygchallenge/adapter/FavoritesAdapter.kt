package dev.com.sfilizzola.gygchallenge.adapter

import android.arch.lifecycle.MutableLiveData
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.R
import dev.com.sfilizzola.gygchallenge.databinding.ReviewRowItemBinding
import dev.com.sfilizzola.gygchallenge.models.Favorite
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.view.viewStatus.FavoriteViewStatus
import dev.com.sfilizzola.gygchallenge.viewmodels.ReviewRowViewModel

class FavoritesAdapter(var data: MutableLiveData<FavoriteViewStatus>) : BaseAdapter<Favorite, ReviewRowItemBinding>(R.layout.review_row_item){

    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.reviewId == newItem.reviewId
    }

    override fun bind(holder: DataBindViewHolder<ReviewRowItemBinding>, position: Int) {
        val currentItem = items[position]

        if (BaseApp.favoriteList.contains(currentItem.reviewId)){
            currentItem.isFavorite = true
        }

        holder.binding.viewModel = ReviewRowViewModel(currentItem, data)
    }

    fun removeItem(favorite: Favorite){
        items.remove(favorite)
        notifyDataSetChanged()
    }

}