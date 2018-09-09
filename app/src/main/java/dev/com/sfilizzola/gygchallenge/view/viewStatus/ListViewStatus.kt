package dev.com.sfilizzola.gygchallenge.view.viewStatus

import dev.com.sfilizzola.gygchallenge.models.Review

sealed class ListViewStatus {
    data class Success(val list:List<Review>):ListViewStatus()
    data class Error(val error:String?):ListViewStatus()
    data class Click(val review:Review):ListViewStatus()

    fun list():List<Review>{
        return when(this){
            is Success -> this.list
            else -> ArrayList<Review>()
        }
    }

    fun error():String?{
        return when(this){
            is Error -> this.error
            else -> null
        }
    }

    fun review():Review?{
        return when(this){
            is Click -> this.review
            else -> null
        }
    }
}