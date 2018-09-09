package dev.com.sfilizzola.gygchallenge.repos

import android.util.AndroidException
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.database.daos.ReviewDao
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.network.NetworkClient
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DataRepository @Inject constructor(private var service: NetworkClient,
                                         private var reviewDao: ReviewDao) {


    fun getReviews(): Single<List<Review>> {
        return getReviewsFromAPI()
    }

    fun getFavorties():Single<List<Review>>{
        return getReviewsFromDatabase()
    }

    fun getFavoritesId():Single<List<Int>>{
        return getReviewsFromDatabase().flatMapPublisher {
            Flowable.fromIterable(it)
        }.map {
            it.reviewId
        }.toList()
    }

    private fun getReviewsFromAPI(): Single<List<Review>> {
        return service.getReviews().flatMapPublisher {
            Flowable.fromIterable(it.data)
        }.map {
            Review(it.review_id, it.rating, it.title, it.message, it.author, it.foreignLanguage, it.date, it.languageCode, it.traveler_type, it.reviewerName, it.reviewerCountry)
        }.toList()
    }


    private fun getReviewsFromDatabase(): Single<List<Review>> {
        return reviewDao.getAllReviews().flatMap {
            if (it.isEmpty()) {
                Single.just(ArrayList())
            } else {
                Single.just(it)
            }
        }
    }

    fun saveReview(review: Review) {
        Timber.d("Saving from Repository %s", review.toString())
        BaseApp.addToFavorite(review.reviewId)
        Completable.fromAction{reviewDao.insert(review)}.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe ()
    }

    fun deleteReview(review: Review) {
        Timber.d("Deleting from Repository %s", review.toString())
        BaseApp.removeFromFavorite(review.reviewId)
        Completable.fromAction {  reviewDao.delete(review) }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

}