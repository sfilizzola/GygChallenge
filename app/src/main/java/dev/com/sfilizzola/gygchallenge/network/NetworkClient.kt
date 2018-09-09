package dev.com.sfilizzola.gygchallenge.network

import dev.com.sfilizzola.gygchallenge.network.networkModels.BasicResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface NetworkClient {

    @GET("reviews.json")
    fun getReviews(@Header("User-Agent") basicHeader:String = "GetYourGuide",
                   @Query("count") reviewsCount:Int = 30,
                   @Query("page") pageIndex:Int = 0,
                   @Query("rating") rating:Int = 0,
                   @Query("sortBy") sortingType:String = "date_of_review",
                   @Query("direction") sortingDirection:String = "DESC") : Single<BasicResponse>

}