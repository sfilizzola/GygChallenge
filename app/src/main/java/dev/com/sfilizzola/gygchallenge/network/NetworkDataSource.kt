package dev.com.sfilizzola.gygchallenge.network

import android.arch.paging.PageKeyedDataSource
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.models.ReviewsParams
import dev.com.sfilizzola.gygchallenge.models.status.NetworkStatus
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor

class NetworkDataSource(private var repository: DataRepository,
                        private var compositeDisposable: CompositeDisposable,
                        private var reviewParams: ReviewsParams) : PageKeyedDataSource<Int, Review>(){

   var status: PublishProcessor<NetworkStatus> = PublishProcessor.create<NetworkStatus>()
    private set

    companion object {
        const val PAGE_SIZE = 50
        const val INITIAL_SIZE = 50
        const val START_INDEX = 0
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Review>) {

        status.onNext(NetworkStatus.Loading)

        compositeDisposable.add(repository.getReviews(PAGE_SIZE, START_INDEX, 0, reviewParams.sortBy, reviewParams.direction)
                .subscribe({
                    status.onNext(NetworkStatus.Success)
                    callback.onResult(it, null, 1)
                },{
                    status.onNext(NetworkStatus.Error(it.localizedMessage))
                }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Review>) {
        compositeDisposable.add(repository.getReviews(PAGE_SIZE, params.key, 0, reviewParams.sortBy, reviewParams.direction)
                .subscribe({
                    status.onNext(NetworkStatus.Success)
                    callback.onResult(it, params.key + 1)
                },{
                    status.onNext(NetworkStatus.Error(it.localizedMessage))
                }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Review>) {
        // none
    }

}