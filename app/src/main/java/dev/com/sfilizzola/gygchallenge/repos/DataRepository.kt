package dev.com.sfilizzola.gygchallenge.repos

import android.util.AndroidException
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.database.daos.FavoritesDao
import dev.com.sfilizzola.gygchallenge.database.daos.ReviewDao
import dev.com.sfilizzola.gygchallenge.models.Favorite
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
                                         private var reviewDao: ReviewDao,
                                         private var favoritesDao: FavoritesDao) {


    fun getReviews(): Single<List<Review>> {
        return getReviewsFromDatabase()
    }

    fun getFavorties(): Single<List<Favorite>> {
        return getFavoritesFromDatabase()
    }

    fun getFavoritesId(): Single<List<Int>> {
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
        }.toList().flatMap {
            saveAllReviews(it)
        }
    }


    private fun getReviewsFromDatabase(): Single<List<Review>> {
        return reviewDao.getAllReviews().flatMap {
            if (it.isEmpty()) {
                getReviewsFromAPI()
            } else {
                Single.just(it)
            }
        }
    }

    private fun getFavoritesFromDatabase(): Single<List<Favorite>> {
        return favoritesDao.getAllFavorites().flatMap {
            if (it.isEmpty()) {
                Single.just(ArrayList())
            } else {
                Single.just(it)
            }
        }
    }


    fun saveFavorites(favorite: Favorite) {
        Timber.d("Saving from Repository %s", favorite.toString())
        BaseApp.addToFavorite(favorite.reviewId)
        Completable.fromAction { favoritesDao.insert(favorite) }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun deleteFavorites(favorite: Favorite) {
        Timber.d("Deleting from Repository %s", favorite.toString())
        BaseApp.removeFromFavorite(favorite.reviewId)
        Completable.fromAction { favoritesDao.delete(favorite) }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun saveAllReviews(reviews: List<Review>): Single<List<Review>> {
        return Completable.fromAction {
            reviewDao.insertAll(reviews)
        }.andThen(Single.just(reviews))
    }

}