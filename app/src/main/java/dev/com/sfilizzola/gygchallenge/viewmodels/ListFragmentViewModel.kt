package dev.com.sfilizzola.gygchallenge.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.databinding.ObservableInt
import android.view.View
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.models.ReviewsParams
import dev.com.sfilizzola.gygchallenge.models.status.NetworkStatus
import dev.com.sfilizzola.gygchallenge.network.NetworkDataSource
import dev.com.sfilizzola.gygchallenge.network.NetworkDataSourceFactory
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ListFragmentViewModel @Inject constructor(private var repository: DataRepository) : BaseViewModel() {

    var recyclerVisibility = ObservableInt(View.GONE)
    var progressVisibility = ObservableInt(View.VISIBLE)

    private var factory = NetworkDataSourceFactory(repository, compositeDisposable)
    private var data = MutableLiveData<ListViewStatus>()

    var pagedData: LiveData<PagedList<Review>>
        private set


    init {
        compositeDisposable.add(factory.getPublishProcessor().subscribe{
            when(it){
                is NetworkStatus.Loading -> showLoading(true)
                is NetworkStatus.Success -> showLoading(false)
                is NetworkStatus.Error -> {
                    showLoading(false)
                    //TODO -- show error
                }
            }
        })

        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
                .setInitialLoadSizeHint(NetworkDataSource.INITIAL_SIZE)
                .setPageSize(NetworkDataSource.PAGE_SIZE)
                .build()

        pagedData = LivePagedListBuilder(factory, pagedListConfig).build()
    }


    fun getReviews(params: ReviewsParams = ReviewsParams()) {
        factory.setParams(params)
        pagedData.value?.dataSource?.invalidate()
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            recyclerVisibility.set(View.GONE)
            progressVisibility.set(View.VISIBLE)
        } else {
            recyclerVisibility.set(View.VISIBLE)
            progressVisibility.set(View.GONE)
        }

    }

    fun getData():MutableLiveData<ListViewStatus> = data

    fun saveReview(review: Review) {
        repository.saveReview(review)
    }

    fun deleteReview(review: Review){
        repository.deleteReview(review)
    }


}