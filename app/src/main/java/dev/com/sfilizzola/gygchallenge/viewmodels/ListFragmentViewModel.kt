package dev.com.sfilizzola.gygchallenge.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableInt
import android.view.View
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ListFragmentViewModel @Inject constructor(private var repository: DataRepository) : BaseViewModel(){

    private var data = MutableLiveData<ListViewStatus>()

    var recyclerVisibility = ObservableInt(View.GONE)
    var progressVisibility = ObservableInt(View.VISIBLE)


    fun getReviews(){
        showLoading(true);
        compositeDisposable.add(repository.getReviews().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    data.postValue(ListViewStatus.Success(it))
                    showLoading(false)
                }, {
                    data.postValue(ListViewStatus.Error(it.message))
                    showLoading(false)
                    Timber.e(it)
                }))
    }

    private fun showLoading(show: Boolean) {
        if (show){
            recyclerVisibility.set(View.GONE)
            progressVisibility.set(View.VISIBLE)
        } else {
            recyclerVisibility.set(View.VISIBLE)
            progressVisibility.set(View.GONE)
        }

    }

    fun getData(): MutableLiveData<ListViewStatus> = data


    fun saveReview(review: Review?){
        review?.let {
            repository.saveReview(it)
        }
    }




}