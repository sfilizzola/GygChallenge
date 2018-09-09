package dev.com.sfilizzola.gygchallenge.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import dev.com.sfilizzola.gygchallenge.BR

class ReviewRowViewModel(private val item: Review, private val data: MutableLiveData<ListViewStatus>) : BaseViewModel() {

    @Bindable
    fun getTitle(): String {
        return if (item.title.isNullOrBlank()) {
            "Untitled Review"
        } else {
            item.title!!
        }
    }

    @Bindable
    fun getMessage(): String {
        return if (item.message.isBlank()) {
            "No message"
        } else {
            item.message
        }
    }

    @Bindable
    fun getRating(): String = item.rating

    @Bindable
    fun getDate(): String = item.date

    @Bindable
    fun getAuthorName(): String = item.reviewerName

    @Bindable
    fun getAuthorCountry(): String = item.reviewerCountry

    @Bindable
    fun getFavorite(): Boolean = item.isFavorite


    fun onRowClick() {
        item.isFavorite = !item.isFavorite
        data.postValue(ListViewStatus.Click(item))
        notifyPropertyChanged(BR.favorite)
    }


}