package dev.com.sfilizzola.gygchallenge.database.daos

import android.arch.persistence.room.*
import dev.com.sfilizzola.gygchallenge.models.Favorite
import dev.com.sfilizzola.gygchallenge.models.Review
import io.reactivex.Single

@Dao
interface FavoritesDao{

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Single<List<Favorite>>

    @Transaction
    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

}