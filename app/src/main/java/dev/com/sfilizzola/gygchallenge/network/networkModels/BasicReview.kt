package dev.com.sfilizzola.gygchallenge.network.networkModels

data class BasicReview (var review_id:Int,
                        var rating: String,
                        var title:String?,
                        var message:String,
                        var author:String,
                        var foreignLanguage:Boolean,
                        var date:String,
                        var languageCode:String,
                        var traveler_type:String?,
                        var reviewerName:String,
                        var reviewerCountry:String)