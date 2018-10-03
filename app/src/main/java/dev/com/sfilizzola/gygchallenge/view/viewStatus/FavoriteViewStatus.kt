package dev.com.sfilizzola.gygchallenge.view.viewStatus

import dev.com.sfilizzola.gygchallenge.models.Favorite
import dev.com.sfilizzola.gygchallenge.models.Review

sealed class FavoriteViewStatus {
    data class Success(val list:List<Favorite>):FavoriteViewStatus()
    data class Error(val error:String?):FavoriteViewStatus()
    data class Click(val review: Review):FavoriteViewStatus()

    fun list():List<Favorite>{
        return when(this){
            is Success -> this.list
            else -> ArrayList<Favorite>()
        }
    }

    fun error():String?{
        return when(this){
            is Error -> this.error
            else -> null
        }
    }

    fun review(): Review?{
        return when(this){
            is Click -> this.review
            else -> null
        }
    }
}