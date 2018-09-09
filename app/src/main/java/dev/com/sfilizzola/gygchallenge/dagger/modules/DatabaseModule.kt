package dev.com.sfilizzola.gygchallenge.dagger.modules

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.database.DatabaseClient
import dev.com.sfilizzola.gygchallenge.database.daos.ReviewDao

import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: BaseApp): DatabaseClient {
        return Room.databaseBuilder(application, DatabaseClient::class.java, "gygchallenge-db")
                .build()
    }

    @Provides @Singleton
    fun provideReviewDao(databaseClient: DatabaseClient): ReviewDao {
        return databaseClient.reviewDao()
    }
}