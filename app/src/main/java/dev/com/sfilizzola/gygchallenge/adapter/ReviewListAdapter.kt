package dev.com.sfilizzola.gygchallenge.adapter

import android.arch.lifecycle.MutableLiveData
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.R
import dev.com.sfilizzola.gygchallenge.databinding.ReviewRowItemBinding

import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import dev.com.sfilizzola.gygchallenge.viewmodels.ReviewRowViewModel


class ReviewListAdapter(var data: MutableLiveData<ListViewStatus>) : BaseAdapter<Review, ReviewRowItemBinding>(R.layout.review_row_item){

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.reviewId == newItem.reviewId
    }

    override fun bind(holder: DataBindViewHolder<ReviewRowItemBinding>, position: Int) {
        val currentItem = items[position]

        if (BaseApp.favoriteList.contains(currentItem.reviewId)){
            currentItem.isFavorite = true
        }

        holder.binding.viewModel = ReviewRowViewModel(currentItem, data)
    }

    fun removeItem(reviewItem:Review){
        items.remove(reviewItem)
        notifyDataSetChanged()
    }

}