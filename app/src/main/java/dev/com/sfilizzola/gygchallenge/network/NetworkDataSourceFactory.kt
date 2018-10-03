package dev.com.sfilizzola.gygchallenge.network

import android.app.usage.NetworkStats
import android.arch.paging.DataSource
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.models.ReviewsParams
import dev.com.sfilizzola.gygchallenge.models.status.NetworkStatus
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor


class NetworkDataSourceFactory(private val repository:DataRepository,
                               private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int, Review>() {

    private val status = PublishProcessor.create<NetworkStatus>()
    private var params = ReviewsParams()


    override fun create(): DataSource<Int, Review> {
        val dataSource = NetworkDataSource(repository, compositeDisposable, params)
        dataSource.status.subscribe { status.onNext(it)}

        return dataSource
    }

    fun setParams(params: ReviewsParams){
        this.params = params
    }

    fun getPublishProcessor() : PublishProcessor<NetworkStatus> = status

}