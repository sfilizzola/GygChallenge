package dev.com.sfilizzola.gygchallenge.repos

import dev.com.sfilizzola.gygchallenge.database.daos.ReviewDao
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.network.NetworkClient
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
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

    fun saveReview(review: Review): Single<Review> {
        return Completable.fromAction { reviewDao.insert(review) }.andThen(Single.just(review))
    }

    fun deleteReview(review: Review): Single<Review> {
        return Completable.fromAction { reviewDao.delete(review) }.andThen(Single.just(review))
    }

}