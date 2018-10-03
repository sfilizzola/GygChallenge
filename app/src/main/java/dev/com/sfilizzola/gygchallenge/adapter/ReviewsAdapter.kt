package dev.com.sfilizzola.gygchallenge.adapter

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.R
import dev.com.sfilizzola.gygchallenge.databinding.ReviewRowItemBinding
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import dev.com.sfilizzola.gygchallenge.viewmodels.ReviewRowViewModel

class ReviewsAdapter(var data: MutableLiveData<ListViewStatus>) : PagedListAdapter<Review, ReviewsAdapter.ReviewRowViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRowViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ReviewRowItemBinding>(inflater, R.layout.review_row_item, parent, false)
        return ReviewRowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewRowViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (BaseApp.favoriteList.contains(currentItem?.reviewId)){
            currentItem?.isFavorite = true
        }

        val viewModel = ReviewRowViewModel(currentItem!!, data)
        holder.binding.viewModel = viewModel
        holder.binding.executePendingBindings()
    }


    class ReviewRowViewHolder(val binding: ReviewRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
                    oldItem.reviewId == newItem.reviewId

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
                    oldItem == newItem
        }
    }
}