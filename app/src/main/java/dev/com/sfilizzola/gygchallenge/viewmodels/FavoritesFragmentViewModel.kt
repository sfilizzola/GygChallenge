package dev.com.sfilizzola.gygchallenge.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableInt
import android.view.View
import dev.com.sfilizzola.gygchallenge.models.Favorite
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import dev.com.sfilizzola.gygchallenge.view.viewStatus.FavoriteViewStatus
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FavoritesFragmentViewModel @Inject constructor(private var repository: DataRepository) : BaseViewModel() {

    private var data = MutableLiveData<FavoriteViewStatus>()

    var recyclerVisibility = ObservableInt(View.GONE)
    var progressVisibility = ObservableInt(View.VISIBLE)
    var emptyVisibility = ObservableInt(View.GONE)


    fun getReviews() {
        showLoading(true);
        compositeDisposable.add(repository.getFavorties().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    data.postValue(FavoriteViewStatus.Success(it))
                    showLoading(false)
                }, {
                    data.postValue(FavoriteViewStatus.Error(it.message))
                    showLoading(false)
                    Timber.e(it)
                }))
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

    fun getData(): MutableLiveData<FavoriteViewStatus> = data

    fun deleteReview(favorite: Favorite){
        repository.deleteFavorites(favorite)
    }

    fun showEmptyMessage() {
        recyclerVisibility.set(View.GONE)
        emptyVisibility.set(View.VISIBLE)
    }

    fun reviewToFavoriteMapper(review: Review): Favorite {
        return Favorite(review.reviewId, review.rating, review.title, review.message, review.author, review.foreignLanguage, review.date, review.languageCode, review.traveler_type, review.reviewerName, review.reviewerCountry, review.isFavorite)
    }


}