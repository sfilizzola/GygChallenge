package dev.com.sfilizzola.gygchallenge.viewmodels

import android.arch.lifecycle.MutableLiveData
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import dev.com.sfilizzola.gygchallenge.view.viewStatus.MainViewStatus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private var repository: DataRepository) : BaseViewModel() {

    private var data = MutableLiveData<MainViewStatus>()


    fun getFavorites(){
        compositeDisposable.add(repository.getFavoritesId().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    data.postValue(MainViewStatus.Success(it))

                }, {
                    data.postValue(MainViewStatus.Error(it.message))
                    Timber.e(it)
                }))
    }

    fun getData():MutableLiveData<MainViewStatus> = data
}