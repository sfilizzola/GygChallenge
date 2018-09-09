package dev.com.sfilizzola.gygchallenge.network.networkModels

import com.google.gson.annotations.SerializedName

data class BasicResponse(var status: Boolean,
                         @SerializedName("total_reviews_comments")
                         var totalComments: Int,
                         var data: ArrayList<BasicReview>)