package dev.com.sfilizzola.gygchallenge.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import dev.com.sfilizzola.gygchallenge.database.daos.FavoritesDao
import dev.com.sfilizzola.gygchallenge.database.daos.ReviewDao
import dev.com.sfilizzola.gygchallenge.models.Review

@Database(entities = arrayOf(Review::class), version = 1)
abstract class DatabaseClient : RoomDatabase() {
    abstract fun reviewDao(): ReviewDao
    abstract fun favoriteDao(): FavoritesDao
}