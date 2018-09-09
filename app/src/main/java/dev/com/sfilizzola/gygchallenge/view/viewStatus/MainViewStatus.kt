package dev.com.sfilizzola.gygchallenge.view.viewStatus

sealed class MainViewStatus {
    data class Success(val favoriteList: List<Int>) : MainViewStatus()
    data class Error(val message: String?) : MainViewStatus()

    fun favoriteList():List<Int>{
        return when(this){
            is Success -> this.favoriteList
            else -> ArrayList<Int>()
        }
    }

    fun message():String?{
        return when(this){
            is Error -> this.message
            else -> null
        }
    }
}