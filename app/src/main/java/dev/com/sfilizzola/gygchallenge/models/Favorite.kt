package dev.com.sfilizzola.gygchallenge.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
data class Favorite(
        @PrimaryKey
        var reviewId: Int,
        var rating: String,
        var title: String?,
        var message: String,
        var author: String,
        var foreignLanguage: Boolean,
        var date: String,
        var languageCode: String,
        var traveler_type: String?,
        var reviewerName: String,
        var reviewerCountry: String,
        var isFavorite: Boolean = false) : Serializable