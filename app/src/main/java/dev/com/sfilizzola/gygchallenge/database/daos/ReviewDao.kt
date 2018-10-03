package dev.com.sfilizzola.gygchallenge.database.daos

import android.arch.persistence.room.*
import dev.com.sfilizzola.gygchallenge.models.Review
import io.reactivex.Single


@Dao
interface ReviewDao{

    @Query("SELECT * FROM reviews")
    fun getAllReviews():Single<List<Review>>

    @Transaction
    @Insert
    fun insertAll(reviews:List<Review>)

}