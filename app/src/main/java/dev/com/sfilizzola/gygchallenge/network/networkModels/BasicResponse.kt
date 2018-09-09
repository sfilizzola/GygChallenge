package dev.com.sfilizzola.gygchallenge.network.networkModels

import com.google.gson.annotations.SerializedName

data class BasicResponse(var status: Boolean = false,
                         @SerializedName("total_reviews_comments")
                         var totalComments: Int = 0,
                         var data: ArrayList<BasicReview> = ArrayList())