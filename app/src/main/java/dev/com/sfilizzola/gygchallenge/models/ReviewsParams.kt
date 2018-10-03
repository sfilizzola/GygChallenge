package dev.com.sfilizzola.gygchallenge.models

data class ReviewsParams(
        var direction: String = "DESC",
        var sortBy: String = "date_of_review"
)